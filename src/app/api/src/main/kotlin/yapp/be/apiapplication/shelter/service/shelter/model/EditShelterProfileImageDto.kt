package yapp.be.apiapplication.shelter.service.shelter.model

data class EditShelterProfileImageRequestDto(
    val shelterId: Long,
    val profileImageUrl: String
)
data class EditShelterProfileImageResponseDto(
    val shelterId: Long
)
