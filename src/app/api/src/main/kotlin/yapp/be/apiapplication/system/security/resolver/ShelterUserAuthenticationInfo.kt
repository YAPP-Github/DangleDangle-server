package yapp.be.apiapplication.system.security.resolver

import yapp.be.model.vo.Email

data class ShelterUserAuthenticationInfo(
    val shelterUserId: Long,
    val email: Email
)
