package yapp.be.storage.jpa.volunteerActivity.model.mappers

import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.model.VolunteerActivityJoinQueue
import yapp.be.domain.volunteerActivity.model.VolunteerActivityWaitingQueue
import yapp.be.storage.jpa.volunteerActivity.model.VolunteerActivityEntity
import yapp.be.storage.jpa.volunteerActivity.model.VolunteerActivityJoiningQueueEntity
import yapp.be.storage.jpa.volunteerActivity.model.VolunteerActivityWaitingQueueEntity

fun VolunteerActivity.toEntityModel(): VolunteerActivityEntity {
    return VolunteerActivityEntity(
        id = this.id,
        shelterId = this.shelterId,
        title = this.title,
        recruitNum = this.recruitNum,
        description = this.description,
        ageLimit = this.ageLimit,
        startAt = this.startAt,
        endAt = this.endAt,
        status = this.volunteerActivityStatus,
        category = this.volunteerActivityCategory
    )
}

fun VolunteerActivityEntity.toDomainModel(): VolunteerActivity {
    return VolunteerActivity(
        id = this.id,
        shelterId = this.shelterId,
        title = this.title,
        recruitNum = this.recruitNum,
        description = this.description,
        ageLimit = this.ageLimit,
        startAt = this.startAt,
        endAt = this.endAt,
        volunteerActivityStatus = this.status,
        volunteerActivityCategory = this.category
    )
}

fun VolunteerActivityJoiningQueueEntity.toDomainModel(): VolunteerActivityJoinQueue {
    return VolunteerActivityJoinQueue(
        id = this.id,
        volunteerId = this.volunteerId,
        volunteerActivityId = this.volunteerActivityId
    )
}

fun VolunteerActivityJoinQueue.toEntityModel(): VolunteerActivityJoiningQueueEntity {
    return VolunteerActivityJoiningQueueEntity(
        id = this.id,
        volunteerId = this.volunteerId,
        volunteerActivityId = this.volunteerActivityId
    )
}

fun VolunteerActivityWaitingQueueEntity.toDomainModel(): VolunteerActivityWaitingQueue {
    return VolunteerActivityWaitingQueue(
        id = this.id,
        volunteerId = this.volunteerId,
        volunteerActivityId = this.volunteerActivityId
    )
}
fun VolunteerActivityWaitingQueue.toEntityModel(): VolunteerActivityWaitingQueueEntity {
    return VolunteerActivityWaitingQueueEntity(
        id = this.id,
        volunteerId = this.volunteerId,
        volunteerActivityId = this.volunteerActivityId
    )
}
