package yapp.be.apiapplication.shelter.service.model

data class DeleteVolunteerActivityRequestDto(
    val shelterUserId: Long,
    val volunteerEventId: Long
)
data class DeleteVolunteerActivityResponseDto(
    val volunteerEventId: Long
)
