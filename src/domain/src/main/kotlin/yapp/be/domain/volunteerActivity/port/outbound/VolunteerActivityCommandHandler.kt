package yapp.be.domain.volunteerActivity.port.outbound

import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.model.VolunteerActivityJoinQueue
import yapp.be.domain.volunteerActivity.model.VolunteerActivityWaitingQueue

interface VolunteerActivityCommandHandler {
    fun saveVolunteerActivity(volunteerActivity: VolunteerActivity): VolunteerActivity
    fun saveAllVolunteerActivities(volunteerActivities: Collection<VolunteerActivity>): List<VolunteerActivity>
    fun deleteVolunteerActivityByIdAndShelterId(id: Long, shelterId: Long)

    fun deleteAllVolunteerEventByShelterId(shelterId: Long): List<Long>
    fun saveVolunteerEventJoinQueue(volunteerActivityJoinQueue: VolunteerActivityJoinQueue): VolunteerActivityJoinQueue
    fun saveVolunteerEventJoinQueue(volunteerActivityWaitingQueue: VolunteerActivityWaitingQueue): VolunteerActivityWaitingQueue

    fun saveVolunteerActivityWaitingQueue(volunteerActivityWaitingQueue: VolunteerActivityWaitingQueue): VolunteerActivityWaitingQueue

    fun deleteVolunteerActivityJoiningQueueByVolunteerId(volunteerId: Long)
    fun deleteVolunteerActivityWaitingQueueByVolunteerId(volunteerId: Long)

    fun deleteVolunteerActivityWaitingQueueByVolunteerActivityId(volunteerActivityId: Long)
    fun updateVolunteerActivity(volunteerActivity: VolunteerActivity): VolunteerActivity
    fun deleteVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
        volunteerId: Long,
        volunteerActivityId: Long
    )
    fun deleteVolunteerActivityWaitingQueueByVolunteerIdAndVolunteerActivityId(
        volunteerId: Long,
        volunteerActivityId: Long
    )
    fun deleteVolunteerActivityJoiningQueueByVolunteerActivityId(volunteerActivityId: Long)
}
