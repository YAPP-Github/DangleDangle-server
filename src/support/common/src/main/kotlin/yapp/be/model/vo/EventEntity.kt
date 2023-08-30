package yapp.be.model.vo

import java.time.LocalDateTime

data class UpdateEventEntity(
    val volunteerEvent: VolunteerEventInfo,
    val volunteer: List<VolunteerEventVolunteerInfo>,
)

data class VolunteerReminderEventEntity(
    val volunteerEvent: VolunteerEventInfo,
    val volunteer: List<VolunteerEventVolunteerInfo>,
)

data class ShelterReminderEventEntity(
    val shelter: VolunteerEventShelterInfo,
    val volunteerEvent: VolunteerEventInfo,
)

data class DeleteEventEntity(
    val volunteerEvent: VolunteerEventInfo,
    val volunteer: List<VolunteerEventVolunteerInfo>,
)

data class EnableJoinEventEntity(
    val volunteerEvent: VolunteerEventInfo,
    val waitingVolunteers: List<VolunteerEventVolunteerInfo>,
)

data class VolunteerEventVolunteerInfo(
    val id: Long,
    val nickName: String
)

data class VolunteerEventShelterInfo(
    val id: Long,
    val name: String
)

data class VolunteerEventInfo(
    val id: Long,
    val name: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)
