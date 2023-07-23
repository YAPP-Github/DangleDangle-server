package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.GetTokenUseCase
import yapp.be.domain.port.outbound.TokenQueryHandler

@Service
class GetTokenDomainService(
    private val tokenQueryHandler: TokenQueryHandler
) : GetTokenUseCase {
    override fun getTokensByAuthToken(authToken: String): String? {
        return tokenQueryHandler.getTokensByAuthToken(authToken)
    }

    override fun getTokenByAccessToken(accessToken: String): String? {
        return tokenQueryHandler.getTokenByAccessToken(accessToken)
    }
}
