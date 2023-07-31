package yapp.be.apiapplication.volunteer.service.model

data class EditVolunteerMyProfileRequestDto(
    val nickName: String,
    val phoneNumber: String,
    val alarmEnabled: Boolean
)

data class EditVolunteerMyProfileResponseDto(
    val volunteerId: Long
)
