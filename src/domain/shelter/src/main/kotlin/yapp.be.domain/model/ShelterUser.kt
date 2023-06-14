package yapp.be.domain.model

import yapp.be.model.Email

data class ShelterUser(
    val email: Email,
    val password: String,
    val phoneNumber: String,
)
