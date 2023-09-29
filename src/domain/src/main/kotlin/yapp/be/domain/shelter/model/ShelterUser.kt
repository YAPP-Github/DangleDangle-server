package yapp.be.domain.model

import yapp.be.model.vo.Email

data class ShelterUser(
    val id: Long = 0,
    val email: Email,
    val password: String,
    val shelterId: Long,
    val needToChangePassword: Boolean
)
