package yapp.be.domain.port.inbound.model


data class ModifyUserTokenCommand(
    val userId: Long,
    val oAuth2AccessToken: String,
    val oAuth2RefreshToken: String,
)
