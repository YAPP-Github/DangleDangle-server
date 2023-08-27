package yapp.be.domain.port.outbound

import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue

interface VolunteerEventCommandHandler {
    fun saveVolunteerEvent(volunteerEvent: VolunteerEvent): VolunteerEvent
    fun saveAllVolunteerEvents(volunteerEvents: Collection<VolunteerEvent>): List<VolunteerEvent>
    fun deleteVolunteerEventByIdAndShelterId(id: Long, shelterId: Long)

    fun saveVolunteerEventJoinQueue(volunteerEventJoinQueue: VolunteerEventJoinQueue): VolunteerEventJoinQueue
    fun saveVolunteerEventJoinQueue(volunteerEventWaitingQueue: VolunteerEventWaitingQueue): VolunteerEventWaitingQueue

    fun saveVolunteerEventWaitingQueue(volunteerEventWaitingQueue: VolunteerEventWaitingQueue): VolunteerEventWaitingQueue

    fun deleteVolunteerEventJoiningQueueByVolunteerId(volunteerId: Long)
    fun deleteVolunteerEventWaitingQueueByVolunteerId(volunteerId: Long)

    fun deleteVolunteerEventWaitingQueueByVolunteerEventId(volunteerEventId: Long)
    fun updateVolunteerEvent(volunteerEvent: VolunteerEvent): VolunteerEvent
    fun deleteVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
        volunteerId: Long,
        volunteerEventId: Long
    )
    fun deleteVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(
        volunteerId: Long,
        volunteerEventId: Long
    )
}
