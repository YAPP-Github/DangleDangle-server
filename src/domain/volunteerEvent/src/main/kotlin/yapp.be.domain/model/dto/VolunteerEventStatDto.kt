package yapp.be.domain.model.dto

data class VolunteerVolunteerEventStatDto(
    val done: Int,
    val waiting: Int,
    val joining: Int
)

data class ShelterUserVolunteerEventStatDto(
    val done: Int,
    val inProgress: Int
)
