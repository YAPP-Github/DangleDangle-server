package yapp.be.domain.port.outbound.model

data class ModifyUserTokenRequest(
    val userId: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val oAuth2AccessToken: String,
    val oAuth2RefreshToken: String,
)
