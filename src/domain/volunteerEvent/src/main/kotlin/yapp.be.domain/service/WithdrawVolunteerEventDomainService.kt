package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.WithDrawVolunteerEventUseCase
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler

@Service
class WithdrawVolunteerEventDomainService(
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler
) : WithDrawVolunteerEventUseCase {

    @Transactional
    override fun withdrawJoinQueue(
        volunteerId: Long,
        volunteerEventId: Long
    ) {
        volunteerEventCommandHandler
            .deleteVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )
    }

    @Transactional
    override fun withdrawWaitingQueue(
        volunteerId: Long,
        volunteerEventId: Long
    ) {
        volunteerEventCommandHandler
            .deleteVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )
    }

    @Transactional
    override fun withdrawWaitingQueue(
        volunteerEventId: Long
    ) {
        volunteerEventCommandHandler
            .deleteVolunteerEventWaitingQueueByVolunteerEventId(
                volunteerEventId = volunteerEventId
            )
    }
}
