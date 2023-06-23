package yapp.be.apiapplication.auth.service.model

data class LoginVolunteerRequestDto(
    val authCode: String
)

class LoginVolunteerResponseDto(
    val accessToken: String,
    val refreshToken: String
)
