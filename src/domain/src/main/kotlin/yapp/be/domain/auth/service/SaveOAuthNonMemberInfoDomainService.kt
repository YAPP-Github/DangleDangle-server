package yapp.be.domain.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.auth.port.inbound.SaveOAuthNonMemberInfoUseCase
import yapp.be.domain.auth.port.outbound.OAuthNonMemberInfoCommandHandler
import yapp.be.model.vo.Email
import java.time.Duration

@Service
class SaveOAuthNonMemberInfoDomainService(
    private val oAuthNonMemberInfoCommandHandler: OAuthNonMemberInfoCommandHandler
) : SaveOAuthNonMemberInfoUseCase {

    @Transactional
    override fun saveOAuthNonMemberInfo(email: Email, oAuthIdentifier: String, duration: Duration) {
        return oAuthNonMemberInfoCommandHandler.save(
            email = email,
            oAuthIdentifier = oAuthIdentifier,
            duration = duration
        )
    }
}
