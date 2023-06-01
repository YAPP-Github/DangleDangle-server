package yapp.be.domain.model

data class VolunteerEventWaitingQueue (
    val id: Long,
    val userId: Long,
    val volunteerEventId: Long,
)
