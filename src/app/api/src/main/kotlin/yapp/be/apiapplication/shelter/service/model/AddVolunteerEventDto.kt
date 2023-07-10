package yapp.be.apiapplication.shelter.service.model

import java.time.LocalDateTime
import yapp.be.domain.model.Iteration
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory

data class AddVolunteerEventRequestDto(
    val title: String,
    val description: String?,
    val recruitNum: Int,
    val category: VolunteerEventCategory,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val iteration: Iteration?,
)

data class AddVolunteerEventResponseDto(
    val volunteerEventsId: List<Long>
)
