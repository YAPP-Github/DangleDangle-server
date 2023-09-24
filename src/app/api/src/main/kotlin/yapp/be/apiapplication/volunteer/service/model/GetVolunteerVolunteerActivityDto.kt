package yapp.be.apiapplication.volunteer.service.model

import java.time.LocalDateTime
data class GetVolunteerUpcomingVolunteerEventResponseDto(
    val shelterId: Long,
    val shelterName: String,
    val shelterImageProfileUrl: String?,
    val volunteerEventId: Long,
    val title: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val joiningNum: Int,
    val waitingNum: Int,
)
