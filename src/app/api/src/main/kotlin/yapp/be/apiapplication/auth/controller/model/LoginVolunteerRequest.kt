package yapp.be.apiapplication.auth.controller.model

import jakarta.validation.constraints.NotBlank
import yapp.be.apiapplication.auth.service.model.LoginVolunteerRequestDto

data class LoginVolunteerRequest(
    @field:NotBlank(message = "값이 비어있습니다.")
    val authToken: String,
) {
    fun toDto(): LoginVolunteerRequestDto {
        return LoginVolunteerRequestDto(
            authToken = authToken
        )
    }
}
