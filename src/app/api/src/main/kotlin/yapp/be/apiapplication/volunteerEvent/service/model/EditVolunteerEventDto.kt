package yapp.be.apiapplication.volunteerEvent.service.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus

data class EditVolunteerEventRequestDto(
    val shelterUserId: Long,
    val volunteerEventId: Long,
    val title: String,
    val recruitNum: Int,
    val description: String?,
    val status: VolunteerEventStatus,
    val category: VolunteerEventCategory,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)

data class EditVolunteerEventResponseDto(
    val volunteerEventId: Long
)
