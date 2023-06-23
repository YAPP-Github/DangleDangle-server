package yapp.be.apiapplication.auth.service.model

import yapp.be.model.Email

data class LoginVolunteerRequestDto(
    val email: Email,
    val authCode: String
)

class LoginVolunteerResponseDto(
    val accessToken: String,
    val refreshToken: String
)
