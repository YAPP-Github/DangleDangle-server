package yapp.be.domain.model

data class VolunteerEventJoinQueue(
    val id: Long = 0,
    val volunteerId: Long,
    val volunteerEventId: Long,
)
