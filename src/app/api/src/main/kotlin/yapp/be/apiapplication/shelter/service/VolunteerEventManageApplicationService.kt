package yapp.be.apiapplication.shelter.service

import java.time.LocalDateTime
import java.time.LocalTime
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
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventListRequestDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventListResponseDto
import yapp.be.apiapplication.shelter.service.model.GetSimpleVolunteerEventResponseDto
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.port.inbound.AddVolunteerEventUseCase
import yapp.be.domain.port.inbound.DeleteVolunteerEventUseCase
import yapp.be.domain.port.inbound.EditVolunteerEventUseCase
import yapp.be.domain.port.inbound.shelteruser.GetShelterUserUseCase
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.model.EditVolunteerEventCommand
import yapp.be.domain.service.exceptions.VolunteerEventExceptionType
import yapp.be.exceptions.CustomException
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
            .getShelterUserDetailVolunteerEventInfo(
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
    fun getVolunteerEvents(reqDto: GetShelterUserVolunteerEventListRequestDto): GetShelterUserVolunteerEventListResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(reqDto.shelterUserId)
        val volunteerEvents = getVolunteerEventUseCase
            .getShelterUserVolunteerEventsByDateRange(
                shelterId = shelterUser.shelterId,
                from = reqDto.from,
                to = reqDto.to
            )

        return GetShelterUserVolunteerEventListResponseDto(
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

        if (LocalDateTime.now().isAfter(reqDto.startAt)) {
            throw CustomException(
                type = VolunteerEventExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "날짜와 시간은 현 시점 이후로만 선택 가능합니다."
            )
        }

        if (reqDto.startAt.isAfter(reqDto.endAt)) {
            throw CustomException(
                type = VolunteerEventExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "시작시간은 종료시간 보다 이전이어야 합니다."
            )
        }

        if (reqDto.iteration != null && reqDto.endAt.isAfter(reqDto.iteration.iterationEndAt.atTime(LocalTime.now()))) {
            throw CustomException(
                type = VolunteerEventExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "반복주기 종료일은 종료시간보다 앞에올 수 없습니다."
            )
        }

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
        identifiers = ["reqDto.volunteerEventId"]
    )
    fun editVolunteerEvent(reqDto: EditVolunteerEventRequestDto): EditVolunteerEventResponseDto {
        if (LocalDateTime.now().isAfter(reqDto.startAt)) {
            throw CustomException(
                type = VolunteerEventExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "날짜와 시간은 현 시점 이후로만 선택 가능합니다."
            )
        }

        if (reqDto.startAt.isAfter(reqDto.endAt)) {
            throw CustomException(
                type = VolunteerEventExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "시작시간은 종료시간 보다 이전이어야 합니다."
            )
        }

        val now = LocalDateTime.now()

        val shelterUser = getShelterUserUseCase
            .getShelterUserById(reqDto.shelterUserId)

        val volunteerEvent =
            getVolunteerEventUseCase
                .getShelterUserDetailVolunteerEventInfo(
                    shelterId = shelterUser.shelterId,
                    volunteerEventId = reqDto.volunteerEventId
                )

        if (volunteerEvent.joiningVolunteers.size > reqDto.recruitNum || reqDto.recruitNum == 0) {
            throw CustomException(
                type = VolunteerEventExceptionType.INVALID_RECRUIT_NUM_EDIT,
                message = "정원을 현재 참여 중 인원보다 적게 수정할 수 없습니다."
            )
        }

        if (reqDto.endAt.isBefore(reqDto.startAt) || reqDto.startAt.isBefore(now) || reqDto.endAt.isBefore(now)) {
            throw CustomException(
                type = VolunteerEventExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "잘못된 날짜 설정입니다."
            )
        }

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
        identifiers = ["reqDto.volunteerEventId"]
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
