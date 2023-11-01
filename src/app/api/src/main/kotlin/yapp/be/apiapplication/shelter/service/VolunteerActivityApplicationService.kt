package yapp.be.apiapplication.shelter.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.GetDetailVolunteerActivityResponseDto
import yapp.be.apiapplication.shelter.service.model.GetSimpleVolunteerActivityResponseDto
import yapp.be.apiapplication.shelter.service.model.GetVolunteerActivityRequestDto
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventListRequestDto
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventListResponseDto
import yapp.be.apiapplication.shelter.service.model.ParticipateVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.ParticipateVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.WithdrawVolunteerActivityRequestDto
import yapp.be.apiapplication.shelter.service.model.WithdrawVolunteerActivityResponseDto
import yapp.be.domain.volunteerActivity.model.event.VolunteerActivityWithdrawalEvent
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.inbound.ParticipateVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.inbound.WithDrawVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.service.exceptions.VolunteerActivityExceptionType
import yapp.be.exceptions.CustomException
import yapp.be.lock.DistributedLock
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

@Service
class VolunteerActivityApplicationService(
    private val eventPublisher: ApplicationEventPublisher,
    private val getVolunteerEventUseCase: GetVolunteerActivityUseCase,
    private val withDrawVolunteerEventUseCase: WithDrawVolunteerActivityUseCase,
    private val participationVolunteerEventUseCase: ParticipateVolunteerActivityUseCase,
) {

    @Transactional(readOnly = true)
    fun getVolunteerEvent(
        reqDto: GetVolunteerActivityRequestDto
    ): GetDetailVolunteerActivityResponseDto {
        val volunteerEvent =
            if (reqDto.volunteerId != null) {
                getVolunteerEventUseCase
                    .getMemberDetailVolunteerActivityInfo(
                        shelterId = reqDto.shelterId,
                        volunteerId = reqDto.volunteerId,
                        volunteerActivityId = reqDto.volunteerEventId
                    )
            } else {
                getVolunteerEventUseCase
                    .getNonMemberDetailVolunteerActivityInfo(
                        shelterId = reqDto.shelterId,
                        volunteerActivityId = reqDto.volunteerEventId
                    )
            }

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
    fun getVolunteerEvents(
        reqDto: GetVolunteerEventListRequestDto
    ): GetVolunteerEventListResponseDto {
        val volunteerEvents = if (reqDto.volunteerId != null) {
            getVolunteerEventUseCase.getMemberVolunteerActivitiesByDateRange(
                shelterId = reqDto.shelterId,
                volunteerId = reqDto.volunteerId,
                from = reqDto.from,
                to = reqDto.to
            )
        } else {
            getVolunteerEventUseCase.getNonMemberVolunteerActivitiesByDateRange(
                shelterId = reqDto.shelterId,
                from = reqDto.from,
                to = reqDto.to
            )
        }

        return GetVolunteerEventListResponseDto(
            events = volunteerEvents.map {
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
    @DistributedLock(
        prefix = "volunteerEvent",
        identifiers = ["reqDto.volunteerEventId"]
    )
    fun participateVolunteerEvent(
        reqDto: ParticipateVolunteerEventRequestDto
    ): ParticipateVolunteerEventResponseDto {
        val volunteerEvent = getVolunteerEventUseCase.getMemberDetailVolunteerActivityInfo(
            shelterId = reqDto.shelterId,
            volunteerId = reqDto.volunteerId,
            volunteerActivityId = reqDto.volunteerEventId
        )

        if (volunteerEvent.eventStatus != VolunteerActivityStatus.IN_PROGRESS) {
            throw CustomException(
                type = VolunteerActivityExceptionType.PARTICIPATION_VALIDATION_FAIL,
                message = "모집중인 봉사가 아닙니다."
            )
        }

        val isFullCapacity = volunteerEvent.recruitNum <= volunteerEvent.joiningVolunteers.size

        if (isFullCapacity) {
            participationVolunteerEventUseCase
                .waitingVolunteerEvent(
                    volunteerId = reqDto.volunteerId,
                    volunteerEventId = reqDto.volunteerEventId,
                    joinParticipants = volunteerEvent.joiningVolunteers.map { it.id },
                    waitingParticipants = volunteerEvent.waitingVolunteers.map { it.id }
                )

            return ParticipateVolunteerEventResponseDto(
                type = UserEventParticipationStatus.WAITING,
                volunteerEventId = reqDto.volunteerEventId
            )
        } else {
            participationVolunteerEventUseCase
                .joinVolunteerEvent(
                    volunteerId = reqDto.volunteerId,
                    volunteerEventId = reqDto.volunteerEventId,
                    joinParticipants = volunteerEvent.joiningVolunteers.map { it.id },
                    waitingParticipants = volunteerEvent.waitingVolunteers.map { it.id }
                )

            return ParticipateVolunteerEventResponseDto(
                type = UserEventParticipationStatus.JOINING,
                volunteerEventId = reqDto.volunteerEventId
            )
        }
    }

    @Transactional
    @DistributedLock(
        prefix = "volunteerEvent",
        identifiers = ["reqDto.volunteerEventId"]
    )
    fun withdrawVolunteerEvent(
        reqDto: WithdrawVolunteerActivityRequestDto
    ): WithdrawVolunteerActivityResponseDto {
        val volunteerEvent = getVolunteerEventUseCase.getMemberDetailVolunteerActivityInfo(
            shelterId = reqDto.shelterId,
            volunteerId = reqDto.volunteerId,
            volunteerActivityId = reqDto.volunteerEventId
        )

        val joinParticipants = volunteerEvent.joiningVolunteers.map { it.id }
        val waitingParticipants = volunteerEvent.waitingVolunteers.map { it.id }

        if (joinParticipants.contains(reqDto.volunteerId)) {
            withDrawVolunteerEventUseCase
                .withdrawJoinQueue(
                    volunteerId = reqDto.volunteerId,
                    volunteerEventId = reqDto.volunteerEventId
                )
        } else if (waitingParticipants.contains(reqDto.volunteerId)) {
            withDrawVolunteerEventUseCase
                .withdrawWaitingQueue(
                    volunteerId = reqDto.volunteerId,
                    volunteerEventId = reqDto.volunteerEventId
                )
        } else {
            throw CustomException(
                type = VolunteerActivityExceptionType.PARTICIPATION_INFO_NOT_FOUND,
                message = "봉사 참여 정보를 찾을 수 없습니다."
            )
        }

        eventPublisher.publishEvent(
            VolunteerActivityWithdrawalEvent(
                userId = reqDto.volunteerId,
                shelterId = reqDto.shelterId,
                volunteerActivityId = reqDto.volunteerEventId
            )
        )

        return WithdrawVolunteerActivityResponseDto(
            volunteerId = reqDto.volunteerId,
            volunteerEventId = reqDto.volunteerEventId
        )
    }
}
