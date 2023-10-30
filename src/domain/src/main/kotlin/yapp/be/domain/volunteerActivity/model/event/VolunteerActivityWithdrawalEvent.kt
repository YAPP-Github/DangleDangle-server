package yapp.be.domain.volunteerActivity.model.event

data class VolunteerActivityWithdrawalEvent(
    val userId: Long,
    val shelterId: Long,
    val volunteerActivityId: Long
)
