package yapp.be.domain.model

import yapp.be.model.vo.Email

data class ShelterUser(
    val id: Long = 0,
    val email: Email,
    var password: String,
    val shelterId: Long,
    var needToChangePassword: Boolean
)
