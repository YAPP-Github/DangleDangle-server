package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Event
import yapp.be.domain.port.inbound.AddEventUseCase
import yapp.be.domain.port.inbound.model.CreateEventsCommand
import yapp.be.domain.port.outbound.EventCommandHandler

@Service
class AddEventDomainService(
    private val eventCommandHandler: EventCommandHandler,
) : AddEventUseCase {
    @Transactional
    override fun create(command: CreateEventsCommand) {
        val events = mutableListOf<Event>()
        command.events.map {
            events.add(
                Event(
                    recordId = it.recordId,
                    json = it.json,
                    type = it.type
                )
            )
        }

        if (command.pipelined)
            eventCommandHandler.saveEventsPipeLined(events)
        else
            eventCommandHandler.saveEvents(events)
    }
}
