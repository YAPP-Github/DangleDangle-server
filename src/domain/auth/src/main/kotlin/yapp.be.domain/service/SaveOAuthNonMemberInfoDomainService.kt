package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.SaveOAuthNonMemberInfoUseCase
import yapp.be.domain.port.outbound.OAuthNonMemberInfoCommandHandler
import yapp.be.domain.port.outbound.OAuthNonMemberInfoQueryHandler
import yapp.be.model.Email
import java.time.Duration

@Service
class SaveOAuthNonMemberInfoDomainService(
    private val oAuthNonMemberInfoQueryHandler: OAuthNonMemberInfoQueryHandler,
    private val oAuthNonMemberInfoCommandHandler: OAuthNonMemberInfoCommandHandler
) : SaveOAuthNonMemberInfoUseCase {
    override fun getOAuthNonMemberInfo(email: Email): String? {
        return oAuthNonMemberInfoQueryHandler.get(email)
    }

    override fun saveOAuthNonMemberInfo(email: Email, oAuthIdentifier: String, duration: Duration) {
        return oAuthNonMemberInfoCommandHandler.save(
            email = email,
            oAuthIdentifier = oAuthIdentifier,
            duration = duration
        )
    }
}
