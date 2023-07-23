package yapp.be.apiapplication.auth.service.model

class TokenRefreshRequestDto(
    val accessToken: String,
    val refreshToken: String,
)

class TokenRefreshResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
