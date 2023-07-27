package yapp.be.model.vo

data class AddEventEntity(
    val volunteerEventId: String,
    val shelterId: String,
    val likedVolunteerIds: List<String>,
)

data class ParticipateEventEntity(
    val volunteerEventId: String,
    val volunteerId: String, // 참여 신청한 사람
)

data class WithdrawEventEntity(
    val volunteerEventId: String,
    val waitingVolunteerIds: List<String>, // 대기 신청한 사람
)
