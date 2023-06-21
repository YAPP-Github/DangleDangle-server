package yapp.be.domain.port.inbound.model

import yapp.be.model.Email

data class CreateUserCommand(
    val nickname: String,
    val email: Email,
    val phone: String,
)
