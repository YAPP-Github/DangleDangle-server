package yapp.be.apiapplication.shelter.service.model

import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import java.time.LocalDateTime

data class GetShelterHomeRequestDto(
    val shelterUserId: Long,
    val category: List<VolunteerActivityCategory>?,
    val status: VolunteerActivityStatus?,
    val from: LocalDateTime,
    val to: LocalDateTime,
)
