package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.SendEvent
import yapp.be.domain.port.inbound.AddSendEventUseCase
import yapp.be.domain.port.inbound.model.CreateSendEventCommand
import yapp.be.domain.port.outbound.SendEventCommandHandler
import yapp.be.model.enums.event.NotificationType

@Service
class AddSendEventDomainService(
    private val sendEventCommandHandler: SendEventCommandHandler,
) : AddSendEventUseCase {
    override fun create(command: CreateSendEventCommand) {
        val sendEvents = command.senders.map {
            SendEvent(command.eventType, it, NotificationType.KAKAOTALK, command.json)
        }.toMutableList()

        sendEventCommandHandler.saveSendEventsPipeLined(sendEvents)
    }
}
