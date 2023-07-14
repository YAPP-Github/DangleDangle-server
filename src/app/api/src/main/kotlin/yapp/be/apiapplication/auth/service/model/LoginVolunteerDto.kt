package yapp.be.apiapplication.auth.service.model

data class LoginVolunteerRequestDto(
    val authToken: String
)

class LoginVolunteerResponseDto(
    val accessToken: String,
    val refreshToken: String
)
