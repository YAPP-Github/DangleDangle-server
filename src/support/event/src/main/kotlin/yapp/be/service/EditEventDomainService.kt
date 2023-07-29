package yapp.be.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.model.Event
import yapp.be.model.enums.event.EventStatus
import yapp.be.port.inbound.EditEventUseCase
import yapp.be.port.outbound.EventCommandHandler
import yapp.be.port.outbound.EventQueryHandler

@Service
class EditEventDomainService(
    private val eventCommandHandler: EventCommandHandler,
    private val eventQueryHandler: EventQueryHandler,
) : EditEventUseCase {
    @Transactional
    override fun editStatus(id: Long, status: EventStatus): Event {
        val event = eventQueryHandler.findById(id)
        val updateEvent = Event(
            id = id,
            json = event.json,
            eventType = event.eventType,
            eventStatus = status,
        )
        return eventCommandHandler.update(updateEvent)
    }
}
