package yapp.be.storage.jpa.event.model.mappers

import yapp.be.domain.model.AddVolunteerEventEvent
import yapp.be.domain.model.ParticipateVolunteerEventEvent
import yapp.be.domain.model.WithDrawVolunteerEventEvent
import yapp.be.storage.jpa.event.model.AddVolunteerEventEventEntity
import yapp.be.storage.jpa.event.model.ParticipateVolunteerEventEventEntity
import yapp.be.storage.jpa.event.model.WithDrawVolunteerEventEventEntity

fun AddVolunteerEventEvent.toEntityModel(): AddVolunteerEventEventEntity {
    return AddVolunteerEventEventEntity(
        id = this.id,
        shelterId = this.shelterId,
        volunteerEventId = this.volunteerEventId,
        volunteerId = this.volunteerId,
        eventStatus = this.status,
        createdAt = this.createdAt,
    )
}

fun AddVolunteerEventEventEntity.toDomainModel(): AddVolunteerEventEvent {
    return AddVolunteerEventEvent(
        id = this.id,
        shelterId = this.shelterId,
        volunteerEventId = this.volunteerEventId,
        volunteerId = this.volunteerId,
    )
}

fun ParticipateVolunteerEventEvent.toEntityModel(): ParticipateVolunteerEventEventEntity {
    return ParticipateVolunteerEventEventEntity(
        id = this.id,
        shelterId = this.shelterId,
        volunteerEventId = this.volunteerEventId,
        volunteerId = this.volunteerId,
        eventStatus = this.status,
        createdAt = this.createdAt,
    )
}

fun ParticipateVolunteerEventEventEntity.toDomainModel(): ParticipateVolunteerEventEvent {
    return ParticipateVolunteerEventEvent(
        id = this.id,
        shelterId = this.shelterId,
        volunteerEventId = this.volunteerEventId,
        volunteerId = this.volunteerId,
    )
}

fun WithDrawVolunteerEventEvent.toEntityModel(): WithDrawVolunteerEventEventEntity {
    return WithDrawVolunteerEventEventEntity(
        id = this.id,
        withDrawVolunteerId = this.withDrawVolunteerId,
        waitingVolunteerId = this.waitingVolunteerId,
        volunteerEventId = this.volunteerEventId,
        eventStatus = this.status,
        createdAt = this.createdAt,
    )
}

fun WithDrawVolunteerEventEventEntity.toDomainModel(): WithDrawVolunteerEventEvent {
    return WithDrawVolunteerEventEvent(
        id = this.id,
        withDrawVolunteerId = this.withDrawVolunteerId,
        waitingVolunteerId = this.waitingVolunteerId,
        volunteerEventId = this.volunteerEventId,
    )
}
