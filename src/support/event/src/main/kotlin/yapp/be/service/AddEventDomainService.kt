package yapp.be.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.model.Event
import yapp.be.model.enums.event.EventStatus
import yapp.be.port.inbound.AddEventUseCase
import yapp.be.port.inbound.model.CreateEventCommand
import yapp.be.port.outbound.EventCommandHandler

@Service
class AddEventDomainService(
    private val eventCommandHandler: EventCommandHandler,
) : AddEventUseCase {
    @Transactional
    override fun create(command: CreateEventCommand) {
        val event = Event(
            json = command.json,
            eventType = command.eventType,
            eventStatus = EventStatus.ACTIVE,
        )
        eventCommandHandler.create(event)
    }
}
