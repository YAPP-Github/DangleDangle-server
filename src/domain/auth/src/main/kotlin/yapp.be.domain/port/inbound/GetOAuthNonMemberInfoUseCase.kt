package yapp.be.domain.port.inbound

import yapp.be.model.Email

interface GetOAuthNonMemberInfoUseCase {
    fun get(email: Email): String?
}
