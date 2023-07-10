package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.domain.port.inbound.ParticipateVolunteerEventUseCase
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.domain.service.exceptions.VolunteerEventExceptionType
import yapp.be.exceptions.CustomException

@Service
class ParticipateVolunteerEventDomainService(
    private val volunteerEventQueryHandler: VolunteerEventQueryHandler,
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler
) : ParticipateVolunteerEventUseCase {
    override fun joinVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long
    ): VolunteerEventJoinQueue {
        val prevJoinQueue = volunteerEventQueryHandler
            .findVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )

        val prevWaitingQueue = volunteerEventQueryHandler
            .findVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )

        if (prevJoinQueue != null) {
            throw CustomException(
                type = VolunteerEventExceptionType.ALREADY_PARTICIPATE,
                message = "이미 참여중인 봉사입니다.")
        }

        if (prevWaitingQueue != null) {
            volunteerEventCommandHandler
                .deleteVolunteerEventWaitingQueue(prevWaitingQueue)
        }


        return volunteerEventCommandHandler
            .saveVolunteerEventJoinQueue(VolunteerEventJoinQueue(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            ))
    }

    override fun waitingVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long
    ):VolunteerEventWaitingQueue {
        val prevJoinQueue = volunteerEventQueryHandler
            .findVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )

        val prevWaitingQueue = volunteerEventQueryHandler
            .findVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )

        if(prevJoinQueue != null || prevWaitingQueue != null){
            throw CustomException(
                type = VolunteerEventExceptionType.ALREADY_PARTICIPATE,
                message = "이미 봉사에 참여중이거나 대기중입니다."
            )
        }

        return volunteerEventCommandHandler.saveVolunteerEventWaitingQueue(
            VolunteerEventWaitingQueue(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )
        )
    }
}
