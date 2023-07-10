package yapp.be.domain.model

data class VolunteerEventWaitingQueue(
    val id: Long = 0,
    val volunteerId: Long,
    val volunteerEventId: Long,
)
