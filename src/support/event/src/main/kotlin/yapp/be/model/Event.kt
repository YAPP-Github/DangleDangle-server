package yapp.be.model

import yapp.be.model.enums.event.EventStatus
import yapp.be.model.enums.event.EventType

data class Event(
    var id: Long = 0,
    var json: String,
    var eventType: EventType,
    var eventStatus: EventStatus,
)
