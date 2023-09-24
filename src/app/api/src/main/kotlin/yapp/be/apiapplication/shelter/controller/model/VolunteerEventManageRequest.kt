package yapp.be.apiapplication.shelter.controller.model

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import yapp.be.model.enums.volunteerActivity.AgeLimit
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import java.time.LocalDateTime
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventRequestDto
import yapp.be.domain.volunteerActivity.model.Iteration
import yapp.be.model.enums.volunteerActivity.IterationCycle
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

data class AddVolunteerEventRequest(
    @field:NotBlank(message = "일정 제목을 입력해주세요")
    var title: String,

    @field:Min(1, message = "최소 한명 이상이어야 합니다.")
    val recruitNum: Int,
    @field:Length(max = 300, message = "입력 가능 글자수를 초과했습니다.")
    var description: String?,
    @field:NotNull(message = "값이 비어있습니다.")
    val category: VolunteerActivityCategory,
    @field:NotNull(message = "값이 비어있습니다.")
    val ageLimit: AgeLimit,
    @field:NotNull(message = "값이 비어있습니다.")
    val startAt: LocalDateTime,
    @field:NotNull(message = "값이 비어있습니다.")
    val endAt: LocalDateTime,
    @field:Valid
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
    @field:Min(1, message = "최소 한명 이상이어야 합니다.")
    val recruitNum: Int,
    @field:Length(max = 300, message = "입력 가능 글자수를 초과했습니다.")
    var description: String?,
    @field:NotNull(message = "이벤트 카테고리 값이 비어있습니다.")
    val category: VolunteerActivityCategory,
    @field:NotNull(message = "이벤트 상태 값이 비어있습니다.")
    val status: VolunteerActivityStatus,
    @field:NotNull(message = "이벤트 나이제한 값이 비어있습니다.")
    val ageLimit: AgeLimit,
    @field:NotNull(message = "시작시간 값이 비어있습니다.")
    val startAt: LocalDateTime,
    @field:NotNull(message = "종료시간 값이 비어있습니다.")
    val endAt: LocalDateTime,
) {
    init {
        title = title.trim()
        description = description?.trim()
    }
}

data class AddVolunteerEventIterationInfo(
    @NotNull(message = "반복 주기의 마감날짜가 비어있습니다.")
    val iterationEndAt: LocalDate,
    @NotNull(message = "반복 주기가 비어있습니다.")
    val iterationCycle: IterationCycle
)
