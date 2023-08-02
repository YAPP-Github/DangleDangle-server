package yapp.be.apiapplication.shelter.service.model

import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import java.time.LocalDateTime

data class GetVolunteerEventHomeRequestDto(
    val volunteerId: Long?,
    val category: VolunteerEventCategory,
    val status: VolunteerEventStatus,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val longitude: Double,
    val latitude: Double,
    val isFavorite: Boolean?,
)

data class GetVolunteerEventHomeResponseDto(
    val volunteerId: Long?,
    val category: VolunteerEventCategory,
    val status: VolunteerEventStatus,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val longitude: Double,
    val latitude: Double,
    val isFavorite: Boolean?,
)
