package yapp.be.domain.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.auth.port.inbound.GetOAuthNonMemberInfoUseCase
import yapp.be.domain.auth.port.outbound.OAuthNonMemberInfoQueryHandler
import yapp.be.model.vo.Email

@Service
class GetOAuthNonMemberInfoDomainService(
    private val oAuthNonMemberInfoQueryHandler: OAuthNonMemberInfoQueryHandler
) : GetOAuthNonMemberInfoUseCase {

    @Transactional(readOnly = true)
    override fun get(email: Email): String? {
        return oAuthNonMemberInfoQueryHandler.get(email)
    }
}
