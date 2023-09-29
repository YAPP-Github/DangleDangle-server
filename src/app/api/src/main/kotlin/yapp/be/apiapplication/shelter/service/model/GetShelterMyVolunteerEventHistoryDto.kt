package yapp.be.apiapplication.shelter.service.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

data class GetShelterMyVolunteerEventHistoryResponseDto(
    val volunteerEventId: Long,
    val title: String,
    val category: VolunteerActivityCategory,
    val eventStatus: VolunteerActivityStatus,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val recruitNum: Int,
    val joiningNum: Int,
    val waitingNum: Int,
)
