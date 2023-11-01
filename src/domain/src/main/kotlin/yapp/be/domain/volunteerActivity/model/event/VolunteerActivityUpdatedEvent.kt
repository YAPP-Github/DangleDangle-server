package yapp.be.domain.volunteerActivity.model.event

data class VolunteerActivityUpdatedEvent(
    val shelterId: Long,
    val volunteerActivityId: Long
)
