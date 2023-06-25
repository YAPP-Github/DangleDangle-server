package yapp.be.domain.model

data class VolunteerEventWaitingQueue(
    val id: Long,
    val volunteerId: Long,
    val volunteerEventId: Long,
)
