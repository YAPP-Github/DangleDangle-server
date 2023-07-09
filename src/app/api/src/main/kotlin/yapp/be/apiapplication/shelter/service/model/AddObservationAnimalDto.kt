package yapp.be.apiapplication.shelter.service.model

import yapp.be.model.enums.observaitonanimal.Gender

data class AddObservationAnimalRequestDto(
    val images: List<String>,
    val name: String,
    val age: String?,
    val gender: Gender?,
    val breed: String?,
    val specialNote: String
)

data class AddObservationAnimalResponseDto(
    val observationAnimalId: Long
)

data class EditObservationAnimalRequestDto(
    val images: List<String>,
    val name: String,
    val age: String?,
    val gender: Gender?,
    val breed: String?,
    val specialNote: String
)
data class EditObservationAnimalResponseDto(
    val observationAnimalId: Long
)
