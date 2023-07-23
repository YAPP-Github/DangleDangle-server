package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.GetTokenUseCase
import yapp.be.domain.port.outbound.TokenQueryHandler

@Service
class GetTokenDomainService(
    private val tokenQueryHandler: TokenQueryHandler
) : GetTokenUseCase {
    @Transactional(readOnly = true)
    override fun getTokensByAuthToken(authToken: String): String? {
        return tokenQueryHandler.getTokensByAuthToken(authToken)
    }

    @Transactional(readOnly = true)
    override fun getTokenByAccessToken(accessToken: String): String? {
        return tokenQueryHandler.getTokenByAccessToken(accessToken)
    }
}
