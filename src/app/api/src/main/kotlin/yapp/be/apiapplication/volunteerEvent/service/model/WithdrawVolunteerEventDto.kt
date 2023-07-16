package yapp.be.apiapplication.volunteerEvent.service.model

data class WithdrawVolunteerEventRequestDto(
    val volunteerId: Long,
    val shelterId: Long,
    val volunteerEventId: Long,
)

data class WithdrawVolunteerEventResponseDto(
    val volunteerId: Long,
    val volunteerEventId: Long
)
