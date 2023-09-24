package yapp.be.domain.volunteer.model

import yapp.be.model.enums.volunteerActivity.OAuthType
import yapp.be.model.enums.volunteerActivity.Role
import yapp.be.model.vo.Email

data class Volunteer(
    val id: Long = 0,
    val email: Email,
    val nickname: String,
    val phone: String,
    val role: Role = Role.VOLUNTEER,
    val oAuthType: OAuthType = OAuthType.KAKAO,
    val oAuthIdentifier: String,
    val oAuthAccessToken: String? = null,
    val oAuthRefreshToken: String? = null,
    val isDeleted: Boolean = false,
    val alarmEnabled: Boolean = true,
)
