package yapp.be.apiapplication.auth.service.model

import yapp.be.model.Email

data class LoginUserRequestDto(
    val email: Email,
    val accessToken: String
)

class LoginUserResponseDto(
    val accessToken: String,
    val refreshToken: String
)
