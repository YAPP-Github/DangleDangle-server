package yapp.be.apiapplication.auth.controller.model

import jakarta.validation.constraints.Pattern
import yapp.be.model.support.PASSWORD_REGEX

data class ChangeShelterUserPasswordRequest(
    @field:Pattern(
        regexp = PASSWORD_REGEX,
        message = "비밀번호는 영문, 숫자, 특수문자 2가지 조합 8~15자이어야 합니다."
    )
    val password: String
)
