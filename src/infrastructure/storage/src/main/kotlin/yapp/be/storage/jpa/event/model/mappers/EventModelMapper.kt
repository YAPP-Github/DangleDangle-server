package yapp.be.storage.jpa.event.model.mappers

import yapp.be.model.Event
import yapp.be.storage.jpa.event.model.EventEntity

fun Event.toEntityModel(): EventEntity {
    return EventEntity(
        id = id,
        json = this.json,
        eventType = this.eventType,
        eventStatus = this.eventStatus,
    )
}

fun EventEntity.toDomainModel(): Event {
    return Event(
        id = id,
        json = this.json,
        eventType = this.eventType,
        eventStatus = this.eventStatus,
    )
}
