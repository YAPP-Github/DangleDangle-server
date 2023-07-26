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
        recordId: String,
        json: String,
        type: EventType,
    ): Event {
        val event = Event(
            recordId = recordId,
            json = json,
            type = type
        )
        return eventCommandHandler.saveEvent(event)
    }
}
