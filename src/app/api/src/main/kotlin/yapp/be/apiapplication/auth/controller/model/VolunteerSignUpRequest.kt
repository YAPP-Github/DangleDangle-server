package yapp.be.apiapplication.auth.controller.model

import yapp.be.apiapplication.auth.service.model.SignUpUserRequestDto
import yapp.be.model.vo.Email

data class VolunteerSignUpRequest(
    val nickname: String,
    val email: String,
    val phoneNumber: String,
) {
    fun toDto(): SignUpUserRequestDto {
        return SignUpUserRequestDto(
            email = Email(email),
            nickname = nickname,
            phoneNumber = phoneNumber,
        )
    }
}
enum class VolunteerSignUpCheckDuplicationType {
    NICKNAME
}
