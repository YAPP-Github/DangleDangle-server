package yapp.be.domain.volunteer.port.inbound.model

import yapp.be.model.vo.Email

data class CreateUserCommand(
    val nickname: String,
    val email: Email,
    val phone: String,
    val oAuthUserIdentifier: String
)
