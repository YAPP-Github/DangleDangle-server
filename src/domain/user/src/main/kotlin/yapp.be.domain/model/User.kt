package yapp.be.domain.model

data class User (
    val email: String,
    val nickname: String,
    val phone: String,
    val role: Role = Role.VOLUNTEER,
    val oAuthType: OAuthType,
    val oAuthAccessToken: String,
    val isDeleted: Boolean = false,
    val shelterIdentifier: Identifier,
)
