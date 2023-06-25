package yapp.be.domain.model

data class VolunteerEventJoinQueue(
    val id: Long,
    val volunteerId: Long,
    val volunteerEventId: Long,
)
