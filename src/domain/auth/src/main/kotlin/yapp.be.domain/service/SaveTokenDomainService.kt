package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.SaveTokenUseCase
import yapp.be.domain.port.outbound.TokenCommandHandler
import java.time.Duration

@Service
class SaveTokenDomainService(
    private val tokenCommandHandler: TokenCommandHandler,
) : SaveTokenUseCase {
    @Transactional
    override fun saveTokensWithAuthToken(authToken: String, accessToken: String, refreshToken: String, authTokenExpire: Duration) {
        tokenCommandHandler.saveTokensWithAuthToken(
            authToken = authToken,
            accessToken = accessToken,
            refreshToken = refreshToken,
            duration = authTokenExpire
        )
    }

    @Transactional
    override fun saveToken(
        accessToken: String,
        refreshToken: String,
        expire: Duration,
    ) {
        tokenCommandHandler.saveToken(
            accessToken = accessToken,
            refreshToken = refreshToken,
            duration = expire
        )
    }
}
