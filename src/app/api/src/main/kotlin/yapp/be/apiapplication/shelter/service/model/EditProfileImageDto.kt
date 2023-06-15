package yapp.be.apiapplication.shelter.service.model

data class EditProfileImageRequestDto(
    val shelterId: Long,
    val profileImageUrl: String
)
data class EditProfileImageResponseDto(
    val shelterId: Long
)
