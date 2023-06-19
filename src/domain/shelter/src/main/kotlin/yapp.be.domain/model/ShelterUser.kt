package yapp.be.domain.model

import yapp.be.model.Email

data class ShelterUser(
    val id: Long = 0,
    val email: Email,
    val password: String,
    val shelterId: Long
)
