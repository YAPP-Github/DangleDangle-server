package yapp.be.apiapplication.system.security.resolver

import yapp.be.model.Email

data class VolunteerAuthenticationInfo(
    val volunteerId: Long,
    val email: Email
)
