package yapp.be.domain.port.outbound.model

data class CreateUserRequest(
    val nickname: String,
    val email: String,
    val phone: String,
)
