package yapp.be.apiapplication.auth.controller.model

data class SignUpRequest(
    val nickname: String,
    val email: String,
    val phone: String,
)
