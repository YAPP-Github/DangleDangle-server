package yapp.be.apiapplication.shelter.service.model

import java.time.LocalDateTime
import yapp.be.model.enums.volunteerActivity.AgeLimit
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

data class EditVolunteerEventRequestDto(
    val shelterUserId: Long,
    val volunteerEventId: Long,
    val title: String,
    val recruitNum: Int,
    val description: String?,
    val status: VolunteerActivityStatus,
    val category: VolunteerActivityCategory,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)

data class EditVolunteerEventResponseDto(
    val volunteerEventId: Long
)
