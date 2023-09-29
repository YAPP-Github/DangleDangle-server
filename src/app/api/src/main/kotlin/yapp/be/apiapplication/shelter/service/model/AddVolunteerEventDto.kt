package yapp.be.apiapplication.shelter.service.model

import java.time.LocalDateTime
import yapp.be.domain.volunteerActivity.model.Iteration
import yapp.be.model.enums.volunteerActivity.AgeLimit
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory

data class AddVolunteerEventRequestDto(
    val title: String,
    val description: String?,
    val recruitNum: Int,
    val category: VolunteerActivityCategory,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val iteration: Iteration?,
)

data class AddVolunteerEventResponseDto(
    val volunteerEventsId: List<Long>
)
