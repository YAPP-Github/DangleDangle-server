package yapp.be.apiapplication.auth.controller.model

import yapp.be.apiapplication.auth.service.model.LoginShelterUserRequestDto
import yapp.be.model.Email

data class LoginShelterUserRequest(
    val email: String,
    val password: String
) {
    fun toDto(): LoginShelterUserRequestDto {
        return LoginShelterUserRequestDto(
            email = Email(email),
            password = password
        )
    }
}
