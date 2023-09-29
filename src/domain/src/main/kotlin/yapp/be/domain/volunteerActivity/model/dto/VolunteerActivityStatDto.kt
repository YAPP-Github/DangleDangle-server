package yapp.be.domain.volunteerActivity.model.dto

data class VolunteerVolunteerActivityStatDto(
    val done: Int,
    val waiting: Int,
    val joining: Int
)

data class ShelterUserVolunteerActivityStatDto(
    val done: Int,
    val inProgress: Int
)
