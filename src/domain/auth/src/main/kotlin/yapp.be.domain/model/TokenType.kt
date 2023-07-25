package yapp.be.domain.model

enum class SecurityTokenType {
    ACCESS, REFRESH
}

enum class BlackListTokenType(val value: String) {
    LOGOUT("logout")
}
