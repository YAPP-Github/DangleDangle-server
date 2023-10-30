package yapp.be.domain.volunteerActivity.model.event

data class VolunteerActivityDeletedEvent(
    val shelterId: Long,
    val volunteerActivityId: Long,
)
