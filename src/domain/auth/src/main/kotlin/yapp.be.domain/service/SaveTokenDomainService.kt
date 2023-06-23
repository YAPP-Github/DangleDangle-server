package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.SaveTokenUseCase
import yapp.be.domain.port.outbound.TokenCommandHandler

@Service
class SaveTokenDomainService(
    private val tokenCommandHandler: TokenCommandHandler,
) : SaveTokenUseCase {
    override fun saveToken(
        accessToken: String,
        refreshToken: String,
        expire: Long,
    ) {
        tokenCommandHandler.saveToken(accessToken, refreshToken, expire)
    }
}
