package yapp.be.apiapplication.shelter.service.shelter.model

import yapp.be.enums.volunteerevent.AgeLimit
import yapp.be.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.enums.volunteerevent.VolunteerEventCategory
import yapp.be.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.Address
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

data class GetVolunteerEventsRequestDto(
    val shelterId: Long,
    val volunteerId: Long?,
    val year: Year,
    val month: Month
)

data class GetVolunteerEventRequestDto(
    val shelterId: Long,
    val volunteerEventId: Long
)

data class GetVolunteerEventParticipantsRequestDto(
    val shelterId: Long,
    val volunteerEventId: Long
)
data class GetVolunteerEventsResponseDto(
    val events: List<GetSimpleVolunteerEventResponseDto>
)
data class GetSimpleVolunteerEventResponseDto(
    val volunteerEventId: Long,
    val category: VolunteerEventCategory,
    val title: String,
    val eventStatus: VolunteerEventStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val participantNum: Int,
    val waitingNum: Int,
)

data class GetDetailVolunteerEventResponseDto(
    val title: String,
    val recruitNum: Int,
    val address: Address,
    val description: String,
    val ageLimit: AgeLimit,
    val category: VolunteerEventCategory,
    val eventStatus: VolunteerEventStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val joiningVolunteers: List<String>,
    val waitingVolunteers: List<String>
)
data class GetDetailVolunteerEventParticipantsResponseDto(
    val joinVolunteers: List<String>,
    val waitingVolunteers: List<String>
)
