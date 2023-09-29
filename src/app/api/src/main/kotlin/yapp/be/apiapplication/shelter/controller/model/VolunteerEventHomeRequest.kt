package yapp.be.apiapplication.shelter.controller.model

import jakarta.validation.constraints.NotNull
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import java.time.LocalDate

data class VolunteerEventHomeRequest(
    val category: VolunteerActivityCategory?,
    @field:NotNull(message = "시작날짜 값이 비어있습니다.")
    val from: LocalDate,
    @field:NotNull(message = "종료날짜 값이 비어있습니다.")
    val to: LocalDate,
    val longitude: Double?,
    val latitude: Double?,
    val address: String?,
    val isFavorite: Boolean?,
)
