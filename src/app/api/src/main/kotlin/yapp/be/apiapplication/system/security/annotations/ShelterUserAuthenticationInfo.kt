package yapp.be.apiapplication.system.security.annotations

import yapp.be.model.Email

data class ShelterUserAuthenticationInfo(
    val shelterUserId: Long,
    val email: Email
)
