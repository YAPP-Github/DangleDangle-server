package yapp.be.domain.auth.model

enum class SecurityTokenType {
    ACCESS, REFRESH
}

enum class BlackListTokenType(val value: String) {
    LOGOUT("logout")
}
