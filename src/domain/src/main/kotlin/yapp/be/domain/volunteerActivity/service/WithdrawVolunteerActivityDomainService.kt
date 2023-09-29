package yapp.be.domain.volunteerActivity.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteerActivity.port.inbound.WithDrawVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.outbound.VolunteerActivityCommandHandler

@Service
class WithdrawVolunteerActivityDomainService(
    private val volunteerActivityCommandHandler: VolunteerActivityCommandHandler
) : WithDrawVolunteerActivityUseCase {

    @Transactional
    override fun withdrawJoinQueue(
        volunteerId: Long,
        volunteerActivityId: Long
    ) {
        volunteerActivityCommandHandler
            .deleteVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerActivityId = volunteerActivityId
            )
    }

    @Transactional
    override fun withdrawWaitingQueue(
        volunteerId: Long,
        volunteerActivityId: Long
    ) {
        volunteerActivityCommandHandler
            .deleteVolunteerActivityWaitingQueueByVolunteerIdAndVolunteerActivityId(
                volunteerId = volunteerId,
                volunteerActivityId = volunteerActivityId
            )
    }

    @Transactional
    override fun withdrawWaitingQueue(
        volunteerActivityId: Long
    ) {
        volunteerActivityCommandHandler
            .deleteVolunteerActivityWaitingQueueByVolunteerActivityId(
                volunteerActivityId = volunteerActivityId
            )
    }
}
