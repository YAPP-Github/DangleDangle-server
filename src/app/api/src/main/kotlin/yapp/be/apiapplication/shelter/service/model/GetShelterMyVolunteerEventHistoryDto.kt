package yapp.be.apiapplication.shelter.service.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus

data class GetShelterMyVolunteerEventHistoryResponseDto(
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
