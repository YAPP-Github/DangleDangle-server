package yapp.be.storage.jpa.event.model.mappers

import yapp.be.domain.model.Event
import yapp.be.storage.jpa.event.model.EventEntity

fun Event.toEntityModel(): EventEntity {
    return EventEntity(
        recordId = this.recordId,
        json = this.json,
        eventType = this.type,
    )
}

fun EventEntity.toDomainModel(): Event {
    return Event(
        recordId = this.recordId,
        json = this.json,
        type = this.eventType,
    )
}
