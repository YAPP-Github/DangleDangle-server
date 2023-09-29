package yapp.be.domain.volunteer.port.inbound.model

data class EditUserTokenCommand(
    val userId: Long,
    val oAuth2AccessToken: String,
    val oAuth2RefreshToken: String,
)
