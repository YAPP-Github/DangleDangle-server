package yapp.be.apiapplication.auth.controller.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import yapp.be.apiapplication.auth.service.model.SignUpUserRequestDto
import yapp.be.model.vo.Email

data class VolunteerSignUpRequest(
    @field:NotBlank(message = "값이 비어있습니다.")
    val nickname: String,
    @field:Pattern(regexp = "^[a-zA-Z0-9._%+-]@[a-zA-Z0-9-]\\.[a-zA-Z0-9-.]\$", message = "잘못된 이메일 형식입니다.")
    val email: String,
    @field:Pattern(
        regexp = "^\\d{3}\\d{3,4}\\d{4}\$",
        message = "입력이 불가능한 문자가 포함되어 있어요"
    )
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
