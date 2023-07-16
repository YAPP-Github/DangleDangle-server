package yapp.be.apiapplication.auth.controller.model

import jakarta.validation.constraints.Pattern
import yapp.be.apiapplication.auth.service.model.LoginShelterUserRequestDto
import yapp.be.model.vo.Email

data class LoginShelterUserRequest(
    @field:Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9-.]+\$", message = "잘못된 이메일 형식입니다.")
    val email: String,
    @field:Pattern(
        regexp = "^(?!((?:[A-Za-z]+)|(?:[~!@#\$%^&*()_+=]+)|(?:[0-9]+))\$)[A-Za-z\\d~!@#\$%^&*()_+=]{8,15}\$",
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
