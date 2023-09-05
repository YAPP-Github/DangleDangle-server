package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.BlackListTokenType
import yapp.be.domain.port.inbound.CheckTokenUseCase
import yapp.be.domain.port.outbound.TokenQueryHandler

@Service
class CheckTokenDomainService(
    private val tokenQueryHandler: TokenQueryHandler,
) : CheckTokenUseCase {

    @Transactional(readOnly = true)
    override fun isValidRefreshToken(accessToken: String, refreshToken: String): Boolean {
        return tokenQueryHandler.getTokenByAccessToken(accessToken = accessToken) == refreshToken
    }

    @Transactional(readOnly = true)
    override fun isTokenBlackList(accessToken: String): Boolean {
        return tokenQueryHandler.getTokenByAccessToken(BlackListTokenType.LOGOUT.value, accessToken) == BlackListTokenType.LOGOUT.value
    }
}
