package yapp.be.domain.volunteerActivity.port.inbound

import yapp.be.domain.volunteerActivity.model.VolunteerActivityJoinQueue
import yapp.be.domain.volunteerActivity.model.VolunteerActivityWaitingQueue

interface ParticipateVolunteerActivityUseCase {
    fun joinVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): VolunteerActivityJoinQueue

    fun waitingVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): VolunteerActivityWaitingQueue
}
