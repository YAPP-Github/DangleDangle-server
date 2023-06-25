package yapp.be.apiapplication.auth.service.model

import yapp.be.model.Email

class SignUpUserRequestDto(
    val nickname: String,
    val email: Email,
    val phone: String,
)

data class SignUpUserWithEssentialInfoResponseDto(
    val userId: Long,
)
data class CheckUserNicknameExistResponseDto(
    val isExist: Boolean
)
