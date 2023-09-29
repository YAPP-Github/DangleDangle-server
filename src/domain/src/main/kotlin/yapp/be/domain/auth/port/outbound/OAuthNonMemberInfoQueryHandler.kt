package yapp.be.domain.auth.port.outbound

import yapp.be.model.vo.Email

interface OAuthNonMemberInfoQueryHandler {
    fun get(email: Email): String?
}
