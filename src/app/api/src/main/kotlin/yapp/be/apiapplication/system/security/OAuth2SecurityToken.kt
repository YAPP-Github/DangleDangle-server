package yapp.be.apiapplication.system.security

data class OAuth2SecurityToken(
    val userId: Long,
    val oAuth2AccessToken: String,
    val oAuth2RefreshToken: String,
)
