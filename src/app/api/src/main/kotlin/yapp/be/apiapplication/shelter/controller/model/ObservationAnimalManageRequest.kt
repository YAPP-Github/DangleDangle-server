package yapp.be.apiapplication.shelter.controller.model

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import yapp.be.apiapplication.shelter.service.model.AddObservationAnimalRequestDto
import yapp.be.apiapplication.shelter.service.model.EditObservationAnimalRequestDto
import yapp.be.model.enums.observaitonanimal.Gender

data class AddObservationAnimalRequest(
    val images: List<String>,
    @field:NotBlank(message = "값이 비어있습니다.")
    @field:Length(max = 20, message = "입력 가능 글자수를 초과했습니다.")
    val name: String,
    @field:Length(max = 20, message = "입력 가능 글자수를 초과했습니다.")
    val age: String?,
    val gender: Gender?,
    @field:Length(max = 20, message = "입력 가능 글자수를 초과했습니다.")
    val breed: String?,
    @field:Length(max = 300, message = "입력 가능 글자수를 초과했습니다.")
    @field:NotBlank(message = "값이 비어있습니다.")
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
    @field:NotBlank(message = "값이 비어있습니다.")
    @field:Length(max = 20, message = "입력 가능 글자수를 초과했습니다.")
    val name: String,
    @field:Length(max = 20, message = "입력 가능 글자수를 초과했습니다.")
    val age: String?,
    val gender: Gender?,
    @field:Length(max = 20, message = "입력 가능 글자수를 초과했습니다.")
    val breed: String?,
    @field:Length(max = 300, message = "입력 가능 글자수를 초과했습니다.")
    @field:NotBlank(message = "값이 비어있습니다.")
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
