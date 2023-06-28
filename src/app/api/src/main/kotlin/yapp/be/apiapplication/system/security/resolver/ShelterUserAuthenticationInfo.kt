package yapp.be.apiapplication.system.security.resolver

import yapp.be.model.Email

data class ShelterUserAuthenticationInfo(
    val shelterUserId: Long,
    val email: Email
)
