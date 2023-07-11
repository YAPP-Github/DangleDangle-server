package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.WithDrawVolunteerEventUseCase
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler
import yapp.be.domain.service.exceptions.VolunteerEventExceptionType
import yapp.be.exceptions.CustomException

@Service
class WithdrawVolunteerEventDomainService(
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler
) : WithDrawVolunteerEventUseCase {
    override fun withdrawVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): Result<Unit> {
        if (joinParticipants.contains(volunteerId)) {
            volunteerEventCommandHandler
                .deleteVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
                    volunteerId = volunteerId,
                    volunteerEventId = volunteerEventId
                )
            return Result.success(Unit)
        }

        if (waitingParticipants.contains(volunteerId)) {
            volunteerEventCommandHandler
                .deleteVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(
                    volunteerId = volunteerId,
                    volunteerEventId = volunteerEventId
                )
            return Result.success(Unit)
        }

        throw CustomException(
            type = VolunteerEventExceptionType.PARTICIPATION_INFO_NOT_FOUND,
            message = "봉사 참여 정보를 찾을 수 없습니다."
        )
    }
}
