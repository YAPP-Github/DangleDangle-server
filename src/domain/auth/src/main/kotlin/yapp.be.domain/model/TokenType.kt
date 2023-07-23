package yapp.be.domain.model

enum class SecurityTokenType {
    ACCESS, REFRESH
}

enum class LogoutTokenType(val value: String) {
    LOGOUT("logout")
}
