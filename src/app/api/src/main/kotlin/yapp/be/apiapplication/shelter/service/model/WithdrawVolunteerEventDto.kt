package yapp.be.apiapplication.shelter.service.model

data class WithdrawVolunteerActivityRequestDto(
    val volunteerId: Long,
    val shelterId: Long,
    val volunteerEventId: Long,
)

data class WithdrawVolunteerActivityResponseDto(
    val volunteerId: Long,
    val volunteerEventId: Long
)
