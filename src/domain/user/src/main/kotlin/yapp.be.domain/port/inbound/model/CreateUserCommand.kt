package yapp.be.domain.port.inbound.model

data class CreateUserCommand(
    val nickname: String,
    val email: String,
    val phone: String,
)
