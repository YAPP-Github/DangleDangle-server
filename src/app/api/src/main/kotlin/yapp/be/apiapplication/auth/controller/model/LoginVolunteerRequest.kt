package yapp.be.apiapplication.auth.controller.model

import yapp.be.apiapplication.auth.service.model.LoginVolunteerRequestDto

data class LoginVolunteerRequest(
    val authToken: String,
) {
    fun toDto(): LoginVolunteerRequestDto {
        return LoginVolunteerRequestDto(
            authToken = authToken
        )
    }
}
