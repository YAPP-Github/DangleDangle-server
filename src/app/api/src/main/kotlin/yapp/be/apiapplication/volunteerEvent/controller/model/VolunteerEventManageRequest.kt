package yapp.be.apiapplication.volunteerEvent.controller.model

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import java.time.LocalDateTime
import yapp.be.apiapplication.volunteerEvent.service.model.AddVolunteerEventRequestDto
import yapp.be.domain.model.Iteration
import yapp.be.model.enums.volunteerevent.IterationCycle
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus

data class AddVolunteerEventRequest(
    @field:NotBlank(message = "일정 제목을 입력해주세요")
    var title: String,
    @field:NotBlank(message = "값이 비어있습니다.")
    @field:Min(1, message = "최소 한명 이상이어야 합니다.")
    val recruitNum: Int,
    @field:Length(max = 300, message = "입력 가능 글자수를 초과했습니다.")
    var description: String?,
    @field:NotBlank(message = "값이 비어있습니다.")
    val category: VolunteerEventCategory,
    @field:NotBlank(message = "값이 비어있습니다.")
    val ageLimit: AgeLimit,
    @field:NotBlank(message = "값이 비어있습니다.")
    val startAt: LocalDateTime,
    @field:NotBlank(message = "값이 비어있습니다.")
    val endAt: LocalDateTime,
    val iteration: AddVolunteerEventIterationInfo?
) {
    init {
        title = title.trim()
        description = description?.trim()
    }
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

data class EditVolunteerEventRequest(
    @field:NotBlank(message = "일정 제목을 입력해주세요")
    var title: String,
    @field:NotBlank(message = "값이 비어있습니다.")
    @field:Min(1, message = "최소 한명 이상이어야 합니다.")
    val recruitNum: Int,
    @field:Length(max = 300, message = "입력 가능 글자수를 초과했습니다.")
    var description: String?,
    @field:NotBlank(message = "값이 비어있습니다.")
    val category: VolunteerEventCategory,
    @field:NotBlank(message = "값이 비어있습니다.")
    val status: VolunteerEventStatus,
    @field:NotBlank(message = "값이 비어있습니다.")
    val ageLimit: AgeLimit,
    @field:NotBlank(message = "값이 비어있습니다.")
    val startAt: LocalDateTime,
    @field:NotBlank(message = "값이 비어있습니다.")
    val endAt: LocalDateTime,
) {
    init {
        title = title.trim()
        description = description?.trim()
    }
}

data class AddVolunteerEventIterationInfo(
    val iterationEndAt: LocalDate,
    val iterationCycle: IterationCycle
)
