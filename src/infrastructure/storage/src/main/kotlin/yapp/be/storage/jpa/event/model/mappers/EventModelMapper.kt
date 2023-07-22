package yapp.be.storage.jpa.event.model.mappers

import yapp.be.domain.model.Event
import yapp.be.storage.jpa.event.model.EventEntity

fun Event.toEntityModel(): EventEntity {
    return EventEntity(
        id = this.id,
        shelterId = this.shelterId,
        volunteerEventId = this.volunteerEventId,
        volunteerId = this.volunteerId,
        eventType = this.type,
        eventStatus = this.status,
        createdAt = this.createdAt,
    )
}

fun EventEntity.toDomainModel(): Event {
    return Event(
        id = this.id,
        shelterId = this.shelterId,
        volunteerEventId = this.volunteerEventId,
        volunteerId = this.volunteerId,
        type = this.eventType,
        status = this.eventStatus,
        createdAt = this.createdAt,
    )
}
