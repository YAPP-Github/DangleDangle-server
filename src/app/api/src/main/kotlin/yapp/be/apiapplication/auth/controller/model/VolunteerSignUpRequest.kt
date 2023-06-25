package yapp.be.apiapplication.auth.controller.model

import yapp.be.apiapplication.auth.service.model.SignUpUserRequestDto
import yapp.be.model.Email

data class VolunteerSignUpRequest(
    val nickname: String,
    val email: String,
    val phone: String,
) {
    fun toDto(): SignUpUserRequestDto {
        return SignUpUserRequestDto(
            email = Email(email),
            nickname = nickname,
            phone = phone,
        )
    }
}
enum class VolunteerSignUpCheckDuplicationType {
    EMAIL
}
