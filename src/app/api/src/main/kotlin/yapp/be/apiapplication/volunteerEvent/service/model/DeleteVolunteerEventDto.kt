package yapp.be.apiapplication.volunteerEvent.service.model

data class DeleteVolunteerEventRequestDto(
    val shelterUserId: Long,
    val volunteerEventId: Long
)
data class DeleteVolunteerEventResponseDto(
    val volunteerEventId: Long
)
