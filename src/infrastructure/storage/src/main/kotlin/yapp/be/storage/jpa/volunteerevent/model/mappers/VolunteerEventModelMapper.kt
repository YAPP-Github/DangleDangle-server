package yapp.be.storage.jpa.volunteerevent.model.mappers

import yapp.be.domain.model.VolunteerEvent
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity

fun VolunteerEvent.toEntityModel(): VolunteerEventEntity {
    return VolunteerEventEntity(
        id = this.id,
        shelterId = this.shelterId,
        title = this.title,
        recruitNum = this.recruitNum,
        description = this.description,
        ageLimit = this.ageLimit,
        startAt = this.startAt,
        endAt = this.endAt,
        status = this.volunteerEventStatus,
        category = this.volunteerEventCategory
    )
}

fun VolunteerEventEntity.toDomainModel(): VolunteerEvent {
    return VolunteerEvent(
        id = this.id,
        shelterId = this.shelterId,
        title = this.title,
        recruitNum = this.recruitNum,
        description = this.description,
        ageLimit = this.ageLimit,
        startAt = this.startAt,
        endAt = this.endAt,
        volunteerEventStatus = this.status,
        volunteerEventCategory = this.category
    )
}
