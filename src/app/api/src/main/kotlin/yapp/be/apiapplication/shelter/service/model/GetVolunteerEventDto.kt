package yapp.be.apiapplication.shelter.service.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerActivity.AgeLimit
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.model.vo.Address

data class GetShelterUserVolunteerActivityRequestDto(
    val shelterUserId: Long,
    val volunteerEventId: Long
)
data class GetVolunteerActivityRequestDto(
    val shelterId: Long,
    val volunteerId: Long?,
    val volunteerEventId: Long
)
data class GetSimpleVolunteerActivityResponseDto(
    val volunteerEventId: Long,
    val category: VolunteerActivityCategory,
    val title: String,
    val eventStatus: VolunteerActivityStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val joiningNum: Int,
    val waitingNum: Int,
)

data class GetDetailVolunteerActivityResponseDto(
    val shelterName: String,
    val shelterProfileImageUrl: String?,
    val title: String,
    val recruitNum: Int,
    val address: Address,
    val description: String,
    val ageLimit: AgeLimit,
    val category: VolunteerActivityCategory,
    val eventStatus: VolunteerActivityStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val joiningVolunteers: List<String>,
    val waitingVolunteers: List<String>
)

data class GetVolunteerMyVolunteerEventHistoryResponseDto(
    val volunteerEventId: Long,
    val shelterName: String,
    val title: String,
    val category: VolunteerActivityCategory,
    val eventStatus: VolunteerActivityStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val participantNum: Int,
    val waitingNum: Int,
)
