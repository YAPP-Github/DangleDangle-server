package yapp.be.domain.port.inbound.model

import yapp.be.model.enums.event.EventType

data class CreateSendEventCommand(
    val senders: List<String>,
    val json: String,
    val eventType: EventType,
)
