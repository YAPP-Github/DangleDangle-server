package yapp.be.domain.volunteerActivity.model.dto

import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import java.time.LocalDateTime

data class VolunteerSimpleVolunteerActivityDto(
    val shelterId: Long,
    val volunteerEventId: Long,
    val shelterName: String,
    val shelterProfileImageUrl: String?,
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

data class ShelterSimpleVolunteerActivityDto(
    val volunteerEventId: Long,
    val title: String,
    val category: VolunteerActivityCategory,
    val eventStatus: VolunteerActivityStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val participantNum: Int,
    val waitingNum: Int,
)
