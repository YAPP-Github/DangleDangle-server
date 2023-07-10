package yapp.be.apiapplication.shelter.controller.model

import java.time.LocalDate
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import java.time.LocalDateTime
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventRequestDto
import yapp.be.domain.model.Iteration
import yapp.be.model.enums.volunteerevent.IterationCycle

data class AddVolunteerEventRequest(
    val title: String,
    val recruitNum: Int,
    val description: String?,
    val category: VolunteerEventCategory,
    val ageLimit: AgeLimit,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val iteration: AddVolunteerEventIterationInfo?
) {
    fun toDto(): AddVolunteerEventRequestDto {
        return AddVolunteerEventRequestDto(
            title = this.title,
            recruitNum = this.recruitNum,
            description = this.description,
            category = this.category,
            ageLimit = this.ageLimit,
            startAt = this.startAt,
            endAt = this.endAt,
            iteration =
            this.iteration?.let {
                Iteration(
                    iterationStartAt = this.startAt.toLocalDate(),
                    iterationEndAt = this.iteration.iterationEndAt,
                    cycle = this.iteration.iterationCycle
                )
            }

        )
    }
}

data class AddVolunteerEventIterationInfo(
    val iterationEndAt: LocalDate,
    val iterationCycle: IterationCycle
)
