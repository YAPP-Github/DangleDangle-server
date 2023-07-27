package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Event
import yapp.be.domain.port.inbound.AddEventUseCase
import yapp.be.domain.port.inbound.model.CreateEventCommand
import yapp.be.domain.port.outbound.EventCommandHandler

@Service
class AddEventDomainService(
    private val eventCommandHandler: EventCommandHandler,
) : AddEventUseCase {
    @Transactional
    override fun create(command: CreateEventCommand) {
        val event = Event(
            recordId = command.recordId,
            json = command.json,
            type = command.type
        )
        eventCommandHandler.saveEvent(event)
    }
}
