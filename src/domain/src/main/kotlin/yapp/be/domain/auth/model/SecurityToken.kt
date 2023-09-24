package yapp.be.domain.auth.model

data class SecurityToken(
    val accessToken: String,
    val refreshToken: String,
)
