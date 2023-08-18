package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.GetOAuthNonMemberInfoUseCase
import yapp.be.domain.port.outbound.OAuthNonMemberInfoQueryHandler
import yapp.be.model.vo.Email

@Service
class GetOAuthNonMemberInfoDomainService(
    private val oAuthNonMemberInfoQueryHandler: OAuthNonMemberInfoQueryHandler
) : GetOAuthNonMemberInfoUseCase {

    @Transactional(readOnly = true)
    override fun get(email: Email): String? {
        val nonMemberInfo = oAuthNonMemberInfoQueryHandler.get(email)
        return nonMemberInfo
    }
}
