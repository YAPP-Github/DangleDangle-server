package yapp.be.apiapplication.shelter.controller.model

import yapp.be.apiapplication.shelter.service.observationanimal.model.AddObservationAnimalRequestDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.EditObservationAnimalRequestDto
import yapp.be.enum.Gender

data class AddObservationAnimalRequest(
    val images: List<String>,
    val name: String,
    val age: Int,
    val gender: Gender,
    val breed: String,
    val specialNote: String

) {
    fun toDto(): AddObservationAnimalRequestDto {
        return AddObservationAnimalRequestDto(
            name = this.name,
            age = this.age,
            gender = this.gender,
            breed = this.breed,
            specialNote = this.specialNote,
            images = this.images
        )
    }
}

data class EditObservationAnimalRequest(
    val images: List<String>,
    val name: String,
    val age: Int,
    val gender: Gender,
    val breed: String,
    val specialNote: String

) {
    fun toDto(): EditObservationAnimalRequestDto {
        return EditObservationAnimalRequestDto(
            name = this.name,
            age = this.age,
            gender = this.gender,
            breed = this.breed,
            specialNote = this.specialNote,
            images = this.images
        )
    }
}
