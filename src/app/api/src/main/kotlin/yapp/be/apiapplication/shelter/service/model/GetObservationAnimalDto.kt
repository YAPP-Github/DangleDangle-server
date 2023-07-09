package yapp.be.apiapplication.shelter.service.model

import yapp.be.model.enums.observaitonanimal.Gender

data class GetShelterUserObservationAnimalResponseDto(
    val id: Long,
    val name: String,
    val age: String?,
    val shelterId: Long,
    val gender: Gender?,
    val breed: String?,
    val profileImageUrl: String?,
    val specialNote: String
)
data class GetObservationAnimalResponseDto(
    val id: Long,
    val name: String,
    val age: String?,
    val shelterId: Long,
    val gender: Gender?,
    val breed: String?,
    val profileImageUrl: String?,
    val specialNote: String
)
