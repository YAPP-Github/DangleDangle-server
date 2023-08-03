package yapp.be.apiapplication.shelter.controller.model

import jakarta.validation.constraints.NotNull
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import java.time.LocalDate

data class VolunteerEventHomeRequest(
    @field:NotNull(message = "이벤트 카테고리 값이 비어있습니다.")
    val category: VolunteerEventCategory,
    @field:NotNull(message = "이벤트 상태 값이 비어있습니다.")
    val status: VolunteerEventStatus,
    @field:NotNull(message = "시작날짜 값이 비어있습니다.")
    val from: LocalDate,
    @field:NotNull(message = "종료날짜 값이 비어있습니다.")
    val to: LocalDate,
    val longitude: Double?,
    val latitude: Double?,
    val address: String?,
    val isFavorite: Boolean?,
)
