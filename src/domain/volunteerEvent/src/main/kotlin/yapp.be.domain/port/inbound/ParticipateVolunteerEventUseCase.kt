package yapp.be.domain.port.inbound

import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue

interface ParticipateVolunteerEventUseCase {
    fun joinVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): VolunteerEventJoinQueue

    fun waitingVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): VolunteerEventWaitingQueue
}
