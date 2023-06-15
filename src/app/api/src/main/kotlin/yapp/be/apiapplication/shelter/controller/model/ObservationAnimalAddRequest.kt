package yapp.be.apiapplication.shelter.controller.model

import yapp.be.enum.Gender

data class ObservationAnimalAddRequest(
    val images: List<String>,
    val name: String,
    val age: Int,
    val gender: Gender,
    val breed: String,
    val specialNote: String
)
