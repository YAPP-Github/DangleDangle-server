package yapp.be.domain.port.outbound

import yapp.be.model.Email

interface OAuthNonMemberInfoQueryHandler {
    fun get(email: Email): String?
}
