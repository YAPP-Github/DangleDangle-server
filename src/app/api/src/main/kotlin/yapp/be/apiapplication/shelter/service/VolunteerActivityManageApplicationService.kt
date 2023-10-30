package yapp.be.apiapplication.shelter.service

import org.springframework.context.ApplicationEventPublisher
import java.time.LocalDateTime
import java.time.LocalTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.DeleteVolunteerActivityRequestDto
import yapp.be.apiapplication.shelter.service.model.DeleteVolunteerActivityResponseDto
import yapp.be.apiapplication.shelter.service.model.EditVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.EditVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.GetDetailVolunteerActivityResponseDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerActivityRequestDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventListRequestDto
import yapp.be.apiapplication.shelter.service.model.GetShelterUserVolunteerEventListResponseDto
import yapp.be.apiapplication.shelter.service.model.GetSimpleVolunteerActivityResponseDto
import yapp.be.domain.volunteerActivity.model.event.VolunteerActivityUpdatedEvent
import yapp.be.domain.port.inbound.shelteruser.GetShelterUserUseCase
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.model.event.VolunteerActivityDeletedEvent
import yapp.be.domain.volunteerActivity.port.inbound.AddVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.inbound.DeleteVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.inbound.EditVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.inbound.model.EditVolunteerActivityCommand
import yapp.be.domain.volunteerActivity.service.exceptions.VolunteerActivityExceptionType
import yapp.be.exceptions.CustomException
import yapp.be.lock.DistributedLock
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

@Service
class VolunteerActivityManageApplicationService(
    private val eventPublisher: ApplicationEventPublisher,
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val getVolunteerActivityUseCase: GetVolunteerActivityUseCase,
    private val addVolunteerActivityUseCase: AddVolunteerActivityUseCase,
    private val editVolunteerActivityUseCase: EditVolunteerActivityUseCase,
    private val deleteVolunteerActivityUseCase: DeleteVolunteerActivityUseCase
) {

    @Transactional(readOnly = true)
    fun getVolunteerEvent(reqDto: GetShelterUserVolunteerActivityRequestDto): GetDetailVolunteerActivityResponseDto {
        val shelterUser = getShelterUserUseCase
            .getShelterUserById(reqDto.shelterUserId)

        val volunteerEvent = getVolunteerActivityUseCase
            .getShelterUserDetailVolunteerActivityInfo(
                shelterId = shelterUser.shelterId,
                volunteerActivityId = reqDto.volunteerEventId
            )

        return GetDetailVolunteerActivityResponseDto(
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
        val volunteerEvents = getVolunteerActivityUseCase
            .getShelterUserVolunteerActivitiesByDateRange(
                shelterId = shelterUser.shelterId,
                from = reqDto.from,
                to = reqDto.to
            )

        return GetShelterUserVolunteerEventListResponseDto(
            volunteerEvents.map {
                GetSimpleVolunteerActivityResponseDto(
                    volunteerEventId = it.volunteerEventId,
                    category = it.category,
                    title = it.title,
                    eventStatus = it.eventStatus,
                    myParticipationStatus = it.myParticipationStatus,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    recruitNum = it.recruitNum,
                    joiningNum = it.joiningNum,
                    waitingNum = it.waitingNum
                )
            }
        )
    }

    @Transactional
    fun addVolunteerActivity(shelterUserId: Long, reqDto: AddVolunteerEventRequestDto): AddVolunteerEventResponseDto {

        if (LocalDateTime.now().isAfter(reqDto.startAt)) {
            throw CustomException(
                type = VolunteerActivityExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "날짜와 시간은 현 시점 이후로만 선택 가능합니다."
            )
        }

        if (reqDto.startAt.isAfter(reqDto.endAt)) {
            throw CustomException(
                type = VolunteerActivityExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "시작시간은 종료시간 보다 이전이어야 합니다."
            )
        }

        if (reqDto.iteration != null && reqDto.endAt.isAfter(reqDto.iteration.iterationEndAt.atTime(LocalTime.now()))) {
            throw CustomException(
                type = VolunteerActivityExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "반복주기 종료일은 종료시간보다 앞에올 수 없습니다."
            )
        }

        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)

        val volunteerActivity = VolunteerActivity(
            title = reqDto.title,
            shelterId = shelterUser.shelterId,
            description = reqDto.description,
            recruitNum = reqDto.recruitNum,
            ageLimit = reqDto.ageLimit,
            volunteerActivityCategory = reqDto.category,
            volunteerActivityStatus = VolunteerActivityStatus.IN_PROGRESS,
            startAt = reqDto.startAt,
            endAt = reqDto.endAt
        )
        val volunteerActivityId =
            reqDto.iteration?.let {
                addVolunteerActivityUseCase.addVolunteerEventsWithIteration(
                    iteration = it,
                    volunteerActivity = volunteerActivity
                )
            } ?: addVolunteerActivityUseCase.addVolunteerEvent(volunteerActivity)

        return AddVolunteerEventResponseDto(volunteerActivityId)
    }

    @Transactional
    @DistributedLock(
        prefix = "volunteerActivity",
        identifiers = ["reqDto.volunteerActivityId"]
    )
    fun editVolunteerActivity(reqDto: EditVolunteerEventRequestDto): EditVolunteerEventResponseDto {
        if (LocalDateTime.now().isAfter(reqDto.startAt)) {
            throw CustomException(
                type = VolunteerActivityExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "날짜와 시간은 현 시점 이후로만 선택 가능합니다."
            )
        }

        if (reqDto.startAt.isAfter(reqDto.endAt)) {
            throw CustomException(
                type = VolunteerActivityExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "시작시간은 종료시간 보다 이전이어야 합니다."
            )
        }

        val now = LocalDateTime.now()

        val shelterUser = getShelterUserUseCase
            .getShelterUserById(reqDto.shelterUserId)

        val volunteerActivity =
            getVolunteerActivityUseCase
                .getShelterUserDetailVolunteerActivityInfo(
                    shelterId = shelterUser.shelterId,
                    volunteerActivityId = reqDto.volunteerEventId,
                )

        if (volunteerActivity.joiningVolunteers.size > reqDto.recruitNum || reqDto.recruitNum == 0) {
            throw CustomException(
                type = VolunteerActivityExceptionType.INVALID_RECRUIT_NUM_EDIT,
                message = "정원을 현재 참여 중 인원보다 적게 수정할 수 없습니다."
            )
        }

        if (reqDto.endAt.isBefore(reqDto.startAt) || reqDto.startAt.isBefore(now) || reqDto.endAt.isBefore(now)) {
            throw CustomException(
                type = VolunteerActivityExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "잘못된 날짜 설정입니다."
            )
        }

        val command = EditVolunteerActivityCommand(
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
        val updatedVolunteerEvent = editVolunteerActivityUseCase
            .editVolunteerEvent(command)
            .also {
                eventPublisher.publishEvent(
                    VolunteerActivityUpdatedEvent(
                        shelterId = it.shelterId,
                        volunteerActivityId = it.id
                    )
                )
            }

        return EditVolunteerEventResponseDto(
            volunteerEventId = updatedVolunteerEvent.id
        )
    }

    @Transactional
    @DistributedLock(
        prefix = "volunteerActivity",
        identifiers = ["reqDto.volunteerActivityId"]
    )
    fun deleteVolunteerActivity(reqDto: DeleteVolunteerActivityRequestDto): DeleteVolunteerActivityResponseDto {
        val shelterUser = getShelterUserUseCase
            .getShelterUserById(reqDto.shelterUserId)

        deleteVolunteerActivityUseCase
            .deleteByIdAndShelterId(
                id = reqDto.volunteerActivityId,
                shelterId = shelterUser.shelterId
            )

        eventPublisher.publishEvent(
            VolunteerActivityDeletedEvent(
                shelterId = shelterUser.shelterId,
                volunteerActivityId = reqDto.volunteerActivityId
            )
        )

        return DeleteVolunteerActivityResponseDto(
            volunteerActivityId = reqDto.volunteerActivityId
        )
    }
}
