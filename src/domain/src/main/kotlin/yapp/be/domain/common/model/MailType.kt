package yapp.be.domain.common.model

enum class MailType(val title: String, val template: String) {
    RESET_PASSWORD("[비밀번호 초기화]", "reset-password")
}
