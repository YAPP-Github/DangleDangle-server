package yapp.be.domain.auth.port.inbound

import yapp.be.model.vo.Email

interface GetOAuthNonMemberInfoUseCase {
    fun get(email: Email): String?
}
