package yapp.be.domain.model.dto

import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import java.time.LocalDateTime

data class VolunteerSimpleVolunteerEventDto(
    val volunteerEventId: Long,
    val title: String,
    val category: VolunteerEventCategory,
    val eventStatus: VolunteerEventStatus,
    val myParticipationStatus: UserEventParticipationStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val participantNum: Int,
    val waitingNum: Int,
)

data class ShelterSimpleVolunteerEventDto(
    val volunteerEventId: Long,
    val title: String,
    val category: VolunteerEventCategory,
    val eventStatus: VolunteerEventStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val participantNum: Int,
    val waitingNum: Int,
)
