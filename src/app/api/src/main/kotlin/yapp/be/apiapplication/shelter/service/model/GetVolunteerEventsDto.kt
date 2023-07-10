package yapp.be.apiapplication.shelter.service.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.vo.Address

data class GetShelterUserVolunteerEventsRequestDto(
    val shelterUserId: Long,
    val from: LocalDateTime,
    val to: LocalDateTime
)
data class GetVolunteerEventsRequestDto(
    val shelterId: Long,
    val volunteerId: Long?,
    val from: LocalDateTime,
    val to: LocalDateTime
)

data class GetVolunteerEventRequestDto(
    val shelterId: Long,
    val volunteerEventId: Long
)

data class GetShelterUserVolunteerEventRequestDto(
    val shelterUserId: Long,
    val volunteerEventId: Long
)

data class GetShelterUserVolunteerEventsResponseDto(
    val events: List<GetSimpleVolunteerEventResponseDto>
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
    val shelterName: String,
    val shelterProfileImageUrl: String?,
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
