package yapp.be.domain.model

data class OAuth2SecurityToken(
    val userId: Long,
    val oAuth2AccessToken: String,
    val oAuth2RefreshToken: String,
)
