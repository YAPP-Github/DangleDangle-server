package yapp.be.port.inbound.model

import yapp.be.model.enums.event.EventType

data class CreateEventCommand(
    val json: String,
    val eventType: EventType,
)
