package yapp.be.domain.model

data class SecurityToken(
    val accessToken: String,
    val refreshToken: String,
)
