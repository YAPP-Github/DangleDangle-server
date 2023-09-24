package yapp.be.domain.volunteerActivity.model

data class VolunteerActivityJoinQueue(
    val id: Long = 0,
    val volunteerId: Long,
    val volunteerActivityId: Long,
)
