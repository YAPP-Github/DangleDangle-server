package yapp.be.port.inbound.model

import yapp.be.model.enums.event.EventType
import yapp.be.model.enums.event.NotificationType

data class CreateSendEventCommand(
    val senders: List<String>,
    val json: String,
    val eventType: EventType,
    val notificationType: NotificationType,
)
