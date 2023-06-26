package yapp.be.apiapplication.system.security

enum class SecurityTokenType(val value: String) {
    ACCESS("ACCESS_TOKEN"), REFRESH("REFRESH_TOKEN")
}
