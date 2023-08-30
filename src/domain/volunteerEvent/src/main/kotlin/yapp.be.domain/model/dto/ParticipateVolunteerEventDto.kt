package yapp.be.domain.model.dto

import java.time.LocalDateTime

data class ReminderVolunteerEventDto(
    val volunteerEventId: Long,
    val shelterId: Long,
    val shelterName: String,
    val title: String,
    val joiningVolunteers: List<VolunteerEventParticipantInfoDto>,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)
