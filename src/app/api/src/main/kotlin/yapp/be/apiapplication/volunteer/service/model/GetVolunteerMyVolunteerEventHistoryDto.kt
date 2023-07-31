package yapp.be.apiapplication.volunteer.service.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
data class GetVolunteerVolunteerEventHistoryResponseDto(
    val volunteerEventId: Long,
    val shelterName: String,
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
