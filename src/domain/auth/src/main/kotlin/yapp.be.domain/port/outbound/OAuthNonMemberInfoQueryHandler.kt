package yapp.be.domain.port.outbound

import yapp.be.model.vo.Email

interface OAuthNonMemberInfoQueryHandler {
    fun get(email: Email): String?
}
