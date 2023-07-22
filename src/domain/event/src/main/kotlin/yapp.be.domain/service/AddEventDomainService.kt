package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Event
import yapp.be.domain.port.inbound.AddEventUseCase
import yapp.be.domain.port.outbound.EventCommandHandler
import yapp.be.model.enums.event.EventType

@Service
class AddEventDomainService(
    private val eventCommandHandler: EventCommandHandler,
) : AddEventUseCase {
    @Transactional
    override fun addEvent(
        volunteerId: Long,
        volunteerEventId: Long,
        shelterId: Long,
        type: EventType,
    ): Event {
        val event = Event(
            volunteerId = volunteerId,
            volunteerEventId = volunteerEventId,
            shelterId = shelterId,
            type = type
        )
        return eventCommandHandler.saveEvent(event)
    }
}
