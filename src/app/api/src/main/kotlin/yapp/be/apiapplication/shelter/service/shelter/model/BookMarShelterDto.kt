package yapp.be.apiapplication.shelter.service.shelter.model

data class BookMarkShelterRequestDto(
    val shelterId: Long,
    val volunteerId: Long
)
data class BookMarkShelterResponseDto(
    val shelterId: Long,
    val volunteerId: Long,
    val isBookMarked: Boolean
)
