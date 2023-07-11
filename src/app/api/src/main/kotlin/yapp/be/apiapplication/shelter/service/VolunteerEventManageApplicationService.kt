package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.DeleteVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.DeleteVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.EditVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.EditVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.GetDetailVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventsRequestDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventsResponseDto
import yapp.be.apiapplication.shelter.service.model.GetSimpleVolunteerEventResponseDto
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.port.inbound.AddVolunteerEventUseCase
import yapp.be.domain.port.inbound.DeleteVolunteerEventUseCase
import yapp.be.domain.port.inbound.EditVolunteerEventUseCase
import yapp.be.domain.port.inbound.shelteruser.GetShelterUserUseCase
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.model.EditVolunteerEventCommand
import yapp.be.lock.DistributedLock
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus

@Service
class VolunteerEventManageApplicationService(
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase,
    private val addVolunteerEventUseCase: AddVolunteerEventUseCase,
    private val editVolunteerEventUseCase: EditVolunteerEventUseCase,
    private val deleteVolunteerEventUseCase: DeleteVolunteerEventUseCase
) {

    @Transactional(readOnly = true)
    fun getVolunteerEvent(reqDto: GetShelterUserVolunteerEventRequestDto): GetDetailVolunteerEventResponseDto {
        val shelterUser = getShelterUserUseCase
            .getShelterUserById(reqDto.shelterUserId)

        val volunteerEvent = getVolunteerEventUseCase
            .getVolunteerEvent(
                shelterId = shelterUser.shelterId,
                volunteerEventId = reqDto.volunteerEventId
            )

        return GetDetailVolunteerEventResponseDto(
            shelterName = volunteerEvent.shelterName,
            shelterProfileImageUrl = volunteerEvent.shelterProfileImageUrl,
            title = volunteerEvent.title,
            recruitNum = volunteerEvent.recruitNum,
            address = volunteerEvent.address,
            description = volunteerEvent.description,
            ageLimit = volunteerEvent.ageLimit,
            category = volunteerEvent.category,
            eventStatus = volunteerEvent.eventStatus,
            myParticipationStatus = volunteerEvent.myParticipationStatus,
            startAt = volunteerEvent.startAt,
            endAt = volunteerEvent.endAt,
            joiningVolunteers = volunteerEvent.joiningVolunteers.map { it.nickName },
            waitingVolunteers = volunteerEvent.waitingVolunteers.map { it.nickName }
        )
    }

    @Transactional(readOnly = true)
    fun getVolunteerEvents(reqDto: GetShelterUserVolunteerEventsRequestDto): GetShelterUserVolunteerEventsResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(reqDto.shelterUserId)
        val volunteerEvents = getVolunteerEventUseCase
            .getShelterUserVolunteerEventsByDateRange(
                shelterId = shelterUser.shelterId,
                from = reqDto.from,
                to = reqDto.to
            )

        return GetShelterUserVolunteerEventsResponseDto(
            volunteerEvents.map {
                GetSimpleVolunteerEventResponseDto(
                    volunteerEventId = it.volunteerEventId,
                    category = it.category,
                    title = it.title,
                    eventStatus = it.eventStatus,
                    myParticipationStatus = it.myParticipationStatus,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    recruitNum = it.recruitNum,
                    joinNum = it.participantNum,
                    waitingNum = it.waitingNum
                )
            }
        )
    }

    @Transactional
    fun addVolunteerEvent(shelterUserId: Long, reqDto: AddVolunteerEventRequestDto): AddVolunteerEventResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)

        val volunteerEvent = VolunteerEvent(
            title = reqDto.title,
            shelterId = shelterUser.shelterId,
            description = reqDto.description,
            recruitNum = reqDto.recruitNum,
            ageLimit = reqDto.ageLimit,
            volunteerEventCategory = reqDto.category,
            volunteerEventStatus = VolunteerEventStatus.IN_PROGRESS,
            startAt = reqDto.startAt,
            endAt = reqDto.endAt
        )
        val volunteerEventsId =
            reqDto.iteration?.let {
                addVolunteerEventUseCase.addVolunteerEventsWithIteration(
                    iteration = it,
                    volunteerEvent = volunteerEvent
                )
            } ?: addVolunteerEventUseCase.addVolunteerEvent(volunteerEvent)

        return AddVolunteerEventResponseDto(volunteerEventsId)
    }

    @Transactional
    @DistributedLock(
        prefix = "volunteerEvent",
        identifiers = ["reqDto.volunteerEventId"],
        timeOut = 3000L,
        leaseTime = 5000L
    )
    fun editVolunteerEvent(reqDto: EditVolunteerEventRequestDto): EditVolunteerEventResponseDto {
        val shelterUser = getShelterUserUseCase
            .getShelterUserById(reqDto.shelterUserId)

        val command = EditVolunteerEventCommand(
            volunteerEventId = reqDto.volunteerEventId,
            shelterId = shelterUser.shelterId,
            title = reqDto.title,
            recruitNum = reqDto.recruitNum,
            description = reqDto.description,
            category = reqDto.category,
            status = reqDto.status,
            ageLimit = reqDto.ageLimit,
            startAt = reqDto.startAt,
            endAt = reqDto.endAt
        )
        val updatedVolunteerEvent = editVolunteerEventUseCase
            .editVolunteerEvent(command)

        return EditVolunteerEventResponseDto(
            volunteerEventId = updatedVolunteerEvent.id
        )
    }

    @Transactional
    @DistributedLock(
        prefix = "volunteerEvent",
        identifiers = ["reqDto.volunteerEventId"],
        timeOut = 3000L,
        leaseTime = 5000L
    )
    fun deleteVolunteerEvent(reqDto: DeleteVolunteerEventRequestDto): DeleteVolunteerEventResponseDto {
        val shelterUser = getShelterUserUseCase
            .getShelterUserById(reqDto.shelterUserId)

        deleteVolunteerEventUseCase
            .deleteByIdAndShelterId(
                id = reqDto.volunteerEventId,
                shelterId = shelterUser.shelterId
            )

        return DeleteVolunteerEventResponseDto(
            volunteerEventId = reqDto.volunteerEventId
        )
    }
}
