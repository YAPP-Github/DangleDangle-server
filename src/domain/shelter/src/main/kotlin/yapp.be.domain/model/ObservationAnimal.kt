package yapp.be.domain.model

import yapp.be.enums.observaitonanimal.Gender

data class ObservationAnimal(
    val id: Long = 0,
    val name: String,
    val age: String?,
    val shelterId: Long,
    val gender: Gender?,
    val breed: String?,
    val profileImageUrl: String?,
    val specialNote: String,
)
