package yapp.be.apiapplication.shelter.service.model

data class WithdrawVolunteerEventRequestDto(
    val volunteerId: Long,
    val shelterId: Long,
    val volunteerEventId: Long,
)

data class WithdrawVolunteerEventResponseDto(
    val volunteerId: Long,
    val volunteerEventId: Long
)
