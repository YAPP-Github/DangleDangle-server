package yapp.be.domain.model.dto

import yapp.be.enums.volunteerevent.UserEventWaitingStatus
import yapp.be.enums.volunteerevent.VolunteerEventStatus
import java.time.LocalDateTime

data class SimpleVolunteerEventInfo(
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
