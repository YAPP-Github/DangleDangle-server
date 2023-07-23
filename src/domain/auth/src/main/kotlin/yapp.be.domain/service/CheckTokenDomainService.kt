package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.CheckTokenUseCase
import yapp.be.domain.port.outbound.TokenQueryHandler

@Service
class CheckTokenDomainService(
    private val tokenQueryHandler: TokenQueryHandler,
) : CheckTokenUseCase {
    override fun checkToken(accessToken: String, refreshToken: String): Boolean {
        return tokenQueryHandler.checkToken(accessToken, refreshToken)
    }
}
