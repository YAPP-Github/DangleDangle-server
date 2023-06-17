package yapp.be.domain.model

class SecurityToken(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
)
