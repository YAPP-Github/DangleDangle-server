package yapp.be.apiapplication.auth.service.model

import yapp.be.model.enums.volunteerevent.Role
import yapp.be.model.vo.Email

class LoginShelterUserRequestDto(
    val email: Email,
    val password: String
)

class LoginShelterUserResponseDto(
    val role: Role,
    val accessToken: String,
    val refreshToken: String
)
