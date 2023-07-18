package yapp.be.apiapplication.auth.controller.model

import jakarta.validation.constraints.Pattern
import yapp.be.apiapplication.auth.service.model.LoginShelterUserRequestDto
import yapp.be.model.support.EMAIL_REGEX
import yapp.be.model.support.PASSWORD_REGEX
import yapp.be.model.vo.Email

data class LoginShelterUserRequest(
    @field:Pattern(regexp = EMAIL_REGEX, message = "잘못된 이메일 형식입니다.")
    val email: String,
    @field:Pattern(
        regexp = PASSWORD_REGEX,
        message = "비밀번호는 영문, 숫자, 특수문자 2가지 조합 8~15자이어야 합니다."
    )
    val password: String
) {
    fun toDto(): LoginShelterUserRequestDto {
        return LoginShelterUserRequestDto(
            email = Email(email),
            password = password
        )
    }
}
