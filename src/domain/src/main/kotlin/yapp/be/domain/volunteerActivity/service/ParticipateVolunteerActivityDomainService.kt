package yapp.be.domain.volunteerActivity.service

import org.springframework.stereotype.Service
import yapp.be.domain.volunteerActivity.model.VolunteerActivityJoinQueue
import yapp.be.domain.volunteerActivity.model.VolunteerActivityWaitingQueue
import yapp.be.domain.volunteerActivity.port.inbound.ParticipateVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.outbound.VolunteerActivityCommandHandler
import yapp.be.domain.volunteerActivity.service.exceptions.VolunteerActivityExceptionType
import yapp.be.exceptions.CustomException

@Service
class ParticipateVolunteerActivityDomainService(
    private val volunteerActivityCommandHandler: VolunteerActivityCommandHandler
) : ParticipateVolunteerActivityUseCase {
    override fun joinVolunteerEvent(
        volunteerId: Long,
        volunteerActivityId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): VolunteerActivityJoinQueue {

        // 이미 참여중이라면 Exception 발생
        if (joinParticipants.contains(volunteerId)) {
            throw CustomException(
                type = VolunteerActivityExceptionType.ALREADY_PARTICIPATE,
                message = "이미 참여중인 봉사입니다."
            )
        }

        // 대기중인 사용자였다면, 대기열 삭제
        if (waitingParticipants.contains(volunteerId)) {
            volunteerActivityCommandHandler
                .deleteVolunteerActivityWaitingQueueByVolunteerIdAndVolunteerActivityId(
                    volunteerId = volunteerId,
                    volunteerActivityId = volunteerActivityId
                )
        }

        // 참여 정보 저장
        return volunteerActivityCommandHandler
            .saveVolunteerEventJoinQueue(
                VolunteerActivityJoinQueue(
                    volunteerId = volunteerId,
                    volunteerActivityId = volunteerActivityId
                )
            )
    }

    override fun waitingVolunteerEvent(
        volunteerId: Long,
        volunteerActivityId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): VolunteerActivityWaitingQueue {

        // 이미 참여중이거나 대기중이라면 Exception 발생
        if (joinParticipants.contains(volunteerId) || waitingParticipants.contains(volunteerId)) {
            throw CustomException(
                type = VolunteerActivityExceptionType.ALREADY_PARTICIPATE,
                message = "이미 봉사에 참여중이거나 대기중입니다."
            )
        }

        // 대기열에 저장
        return volunteerActivityCommandHandler.saveVolunteerActivityWaitingQueue(
            VolunteerActivityWaitingQueue(
                volunteerId = volunteerId,
                volunteerActivityId = volunteerActivityId
            )
        )
    }
}
