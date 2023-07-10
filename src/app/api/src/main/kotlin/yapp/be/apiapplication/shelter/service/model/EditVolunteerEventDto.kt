package yapp.be.apiapplication.shelter.service.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory

data class EditVolunteerEventRequestDto(
    val title: String,
    val recruitNum: Int,
    val description: String?,
    val category: VolunteerEventCategory,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)

data class EditVolunteerEventResponseDto(
    val volunteerEventId: Long
)
