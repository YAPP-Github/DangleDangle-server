package yapp.be.apiapplication.auth.controller.model

import yapp.be.apiapplication.auth.service.model.LoginVolunteerRequestDto

data class LoginVolunteerRequest(
    val authCode: String,
) {
    fun toDto(): LoginVolunteerRequestDto {
        return LoginVolunteerRequestDto(
            authCode = authCode
        )
    }
}
