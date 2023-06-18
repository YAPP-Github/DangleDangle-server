package yapp.be.apiapplication.auth.service.model

import yapp.be.model.Email

class LoginShelterUserRequestDto(
    val email: Email,
    val password: String
)

class LoginShelterUserResponseDto(
    val accessToken: String,
    val refreshToken: String
)
