package yapp.be.storage.jpa.volunteerevent.model.mappers

import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventJoinQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventWaitingQueueEntity

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

fun VolunteerEventJoinQueueEntity.toDomainModel(): VolunteerEventJoinQueue {
    return VolunteerEventJoinQueue(
        id = this.id,
        volunteerId = this.volunteerId,
        volunteerEventId = this.volunteerEventId
    )
}

fun VolunteerEventJoinQueue.toEntityModel(): VolunteerEventJoinQueueEntity {
    return VolunteerEventJoinQueueEntity(
        id = this.id,
        volunteerId = this.volunteerId,
        volunteerEventId = this.volunteerEventId
    )
}

fun VolunteerEventWaitingQueueEntity.toDomainModel(): VolunteerEventWaitingQueue {
    return VolunteerEventWaitingQueue(
        id = this.id,
        volunteerId = this.volunteerId,
        volunteerEventId = this.volunteerEventId
    )
}
fun VolunteerEventWaitingQueue.toEntityModel(): VolunteerEventWaitingQueueEntity {
    return VolunteerEventWaitingQueueEntity(
        id = this.id,
        volunteerId = this.volunteerId,
        volunteerEventId = this.volunteerEventId
    )
}
