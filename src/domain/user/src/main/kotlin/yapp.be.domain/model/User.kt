package yapp.be.domain.model

import yapp.be.enum.OAuthType
import yapp.be.enum.Role

data class User(
    val id: Long,
    val email: String,
    val nickname: String,
    val phone: String,
    val role: Role = Role.VOLUNTEER,
    val oAuthType: OAuthType = OAuthType.KAKAO,
    val oAuthAccessToken: String? = null,
    val oAuthRefreshToken: String? = null,
    val isDeleted: Boolean = false,
)
