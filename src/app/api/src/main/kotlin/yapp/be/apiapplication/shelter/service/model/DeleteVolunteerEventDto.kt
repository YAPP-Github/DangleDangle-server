package yapp.be.apiapplication.shelter.service.model

data class DeleteVolunteerActivityRequestDto(
    val shelterUserId: Long,
    val volunteerActivityId: Long
)
data class DeleteVolunteerActivityResponseDto(
    val volunteerActivityId: Long
)
