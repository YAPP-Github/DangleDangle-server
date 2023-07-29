package yapp.be.model.vo

data class UpdateEventEntity(
    val volunteerEventId: String,
    val volunteerIds: List<String>,
)

data class VolunteerReminderEventEntity(
    val volunteerEventId: String,
    val volunteerIds: List<String>,
)

data class ShelterReminderEventEntity(
    val shelterId: String,
    val volunteerEventId: String,
)

data class DeleteEventEntity(
    val volunteerEventId: String,
    val volunteerIds: List<String>,
)

data class EnableJoinEventEntity(
    val volunteerEventId: String,
    val waitingVolunteerIds: List<String>,
)
