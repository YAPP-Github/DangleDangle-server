package yapp.be.apiapplication.shelter.controller.model

import jakarta.validation.constraints.NotNull
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import java.time.LocalDate

data class ShelterHomeRequest(
    val category: List<VolunteerEventCategory>?,
    val status: VolunteerEventStatus?,
    @field:NotNull(message = "시작날짜 값이 비어있습니다.")
    val from: LocalDate,
    @field:NotNull(message = "종료날짜 값이 비어있습니다.")
    val to: LocalDate,
)
