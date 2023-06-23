package yapp.be.apiapplication.auth.controller.model

import yapp.be.apiapplication.auth.service.model.LoginUserRequestDto
import yapp.be.model.Email

data class LoginUserRequest(
    val email: String,
    val accessToken: String,
) {
    fun toDto(): LoginUserRequestDto {
        return LoginUserRequestDto(
            email = Email(email),
            accessToken = accessToken
        )
    }
}
