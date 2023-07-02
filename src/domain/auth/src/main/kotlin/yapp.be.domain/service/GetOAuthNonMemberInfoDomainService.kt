package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.GetOAuthNonMemberInfoUseCase
import yapp.be.domain.port.outbound.OAuthNonMemberInfoQueryHandler
import yapp.be.model.Email

@Service
class GetOAuthNonMemberInfoDomainService(
    private val oAuthNonMemberInfoQueryHandler: OAuthNonMemberInfoQueryHandler
) : GetOAuthNonMemberInfoUseCase {
    override fun get(email: Email): String? {
        return oAuthNonMemberInfoQueryHandler.get(email)
    }
}
