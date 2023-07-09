package yapp.be.apiapplication.system.security.resolver

import yapp.be.model.vo.Email

data class VolunteerAuthenticationInfo(
    val volunteerId: Long,
    val email: Email
)
