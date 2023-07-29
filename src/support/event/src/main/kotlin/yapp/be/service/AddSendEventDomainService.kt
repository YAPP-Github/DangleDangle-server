package yapp.be.service

import org.springframework.stereotype.Service
import yapp.be.model.SendEvent
import yapp.be.model.enums.event.NotificationType
import yapp.be.port.inbound.AddSendEventUseCase
import yapp.be.port.inbound.model.CreateSendEventCommand
import yapp.be.port.outbound.SendEventCommandHandler

@Service
class AddSendEventDomainService(
    private val sendEventCommandHandler: SendEventCommandHandler,
) : AddSendEventUseCase {
    private val newStreamKey: String = "new-event-stream"
    override fun create(command: CreateSendEventCommand) {
        val sendEvents = command.senders.map {
            SendEvent(
                newStreamKey,
                eventType = command.eventType,
                senderId = it,
                notificationType = NotificationType.KAKAOTALK,
                json = command.json
            )
        }.toMutableList()

        sendEventCommandHandler.saveSendEventsPipeLined(sendEvents)
    }
}
