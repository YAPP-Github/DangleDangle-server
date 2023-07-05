package yapp.be.apiapplication.shelter.service.shelter.model

import yapp.be.enums.volunteerevent.UserEventWaitingStatus
import yapp.be.enums.volunteerevent.VolunteerEventStatus
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

data class GetVolunteerEventsRequestDto(
    val shelterId: Long,
    val volunteerId: Long?,
    val year: Year,
    val month: Month
)

data class GetVolunteerEventsResponseDto(
    val events: List<GetVolunteerSimpleEventResponseDto>
)

data class GetVolunteerSimpleEventResponseDto(
    val volunteerEventId: Long,
    val title: String,
    val eventStatus: VolunteerEventStatus,
    val myParticipationStatus: UserEventWaitingStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val participantNum: Int,
    val waitingNum: Int,
)
