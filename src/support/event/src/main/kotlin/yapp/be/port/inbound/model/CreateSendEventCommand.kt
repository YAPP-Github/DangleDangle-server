package yapp.be.port.inbound.model

import yapp.be.model.enums.event.EventType
import yapp.be.model.enums.event.NotificationType
import java.time.LocalDateTime

data class CreateSendEventCommand(
    val senders: List<SenderInfo>,
    val eventInfo: EventInfo,
    val eventType: EventType,
    val notificationType: NotificationType,
)

data class EventInfo(
    val eventName: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)

data class SenderInfo(
    val id: Long,
    val name: String,
)
