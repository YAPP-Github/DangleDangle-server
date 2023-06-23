package yapp.be.apiapplication.auth.controller.model

import yapp.be.apiapplication.auth.service.model.LoginVolunteerRequestDto
import yapp.be.model.Email

data class LoginVolunteerRequest(
    val email: String,
    val authCode: String,
) {
    fun toDto(): LoginVolunteerRequestDto {
        return LoginVolunteerRequestDto(
            email = Email(email),
            authCode = authCode
        )
    }
}
