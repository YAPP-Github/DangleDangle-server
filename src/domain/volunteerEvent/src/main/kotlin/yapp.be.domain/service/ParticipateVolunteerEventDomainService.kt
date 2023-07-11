package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.domain.port.inbound.ParticipateVolunteerEventUseCase
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler
import yapp.be.domain.service.exceptions.VolunteerEventExceptionType
import yapp.be.exceptions.CustomException

@Service
class ParticipateVolunteerEventDomainService(
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler
) : ParticipateVolunteerEventUseCase {
    override fun joinVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): VolunteerEventJoinQueue {

        // 이미 참여중이라면 Exception 발생
        if (joinParticipants.contains(volunteerId)) {
            throw CustomException(
                type = VolunteerEventExceptionType.ALREADY_PARTICIPATE,
                message = "이미 참여중인 봉사입니다."
            )
        }

        // 대기중인 사용자였다면, 대기열 삭제
        if (waitingParticipants.contains(volunteerId)) {
            volunteerEventCommandHandler
                .deleteVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(
                    volunteerId = volunteerId,
                    volunteerEventId = volunteerEventId
                )
        }

        // 참여 정보 저장
        return volunteerEventCommandHandler
            .saveVolunteerEventJoinQueue(
                VolunteerEventJoinQueue(
                    volunteerId = volunteerId,
                    volunteerEventId = volunteerEventId
                )
            )
    }

    override fun waitingVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): VolunteerEventWaitingQueue {

        // 이미 참여중이거나 대기중이라면 Exception 발생
        if (joinParticipants.contains(volunteerId) || waitingParticipants.contains(volunteerId)) {
            throw CustomException(
                type = VolunteerEventExceptionType.ALREADY_PARTICIPATE,
                message = "이미 봉사에 참여중이거나 대기중입니다."
            )
        }

        // 대기열에 저장
        return volunteerEventCommandHandler.saveVolunteerEventWaitingQueue(
            VolunteerEventWaitingQueue(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )
        )
    }
}
