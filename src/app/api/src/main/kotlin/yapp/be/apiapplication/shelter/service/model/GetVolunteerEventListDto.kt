package yapp.be.apiapplication.shelter.service.model

import java.time.LocalDateTime

data class GetVolunteerEventListRequestDto(
    val shelterId: Long,
    val volunteerId: Long?,
    val from: LocalDateTime,
    val to: LocalDateTime
)

data class GetVolunteerEventListResponseDto(
    val events: List<GetSimpleVolunteerEventResponseDto>
)

data class GetShelterUserVolunteerEventListRequestDto(
    val shelterUserId: Long,
    val from: LocalDateTime,
    val to: LocalDateTime
)

data class GetShelterUserVolunteerEventListResponseDto(
    val events: List<GetSimpleVolunteerEventResponseDto>
)
