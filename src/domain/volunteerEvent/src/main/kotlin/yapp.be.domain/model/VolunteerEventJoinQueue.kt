package yapp.be.domain.model

data class VolunteerEventJoinQueue(
    val id: Long,
    val userId: Long,
    val volunteerEventId: Long,
)
