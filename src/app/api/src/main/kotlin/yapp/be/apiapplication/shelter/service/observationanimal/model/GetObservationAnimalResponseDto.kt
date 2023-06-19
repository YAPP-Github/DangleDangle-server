package yapp.be.apiapplication.shelter.service.observationanimal.model

import yapp.be.enum.Gender

data class GetObservationAnimalResponseDto(
    val id: Long,
    val name: String,
    val age: Int,
    val shelterId: Long,
    val gender: Gender,
    val breed: String,
    val profileImageUrl: String,
    val specialNote: String
)
