package yapp.be.domain.model

data class ShelterBookMark(
    val id: Long = 0,
    val shelterId: Long,
    val volunteerId: Long
)
