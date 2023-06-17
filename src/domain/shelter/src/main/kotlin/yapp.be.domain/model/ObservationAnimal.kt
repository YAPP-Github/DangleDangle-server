package yapp.be.domain.model

import yapp.be.enum.Gender

data class ObservationAnimal(
    val id: Long = 0,
    val name: String,
    val age: Int,
    val shelterId: Long,
    val gender: Gender,
    val profileImageUrl: String,
    val specialNote: String,
)
