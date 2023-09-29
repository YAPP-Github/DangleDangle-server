package yapp.be.apiapplication.auth.controller.model

import jakarta.validation.constraints.Pattern
import yapp.be.model.support.EMAIL_REGEX
import yapp.be.model.support.PHONE_REGEX

data class ResetShelterUserPasswordRequest(
    @field:Pattern(
        regexp = PHONE_REGEX,
        message = "올바른 전화번호 형식인지 확인해주세요. (- 제외 필요)"
    )
    val phoneNumber: String,
    @field:Pattern(regexp = EMAIL_REGEX, message = "잘못된 이메일 형식입니다.")
    val email: String
)
