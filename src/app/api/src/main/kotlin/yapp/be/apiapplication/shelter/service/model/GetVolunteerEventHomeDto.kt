package yapp.be.apiapplication.shelter.service.model

import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import java.time.LocalDateTime

data class GetVolunteerEventHomeRequestDto(
    val volunteerId: Long?,
    val category: VolunteerEventCategory?,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val longitude: Double?,
    val latitude: Double?,
    val address: String?,
    val isFavorite: Boolean?,
)
