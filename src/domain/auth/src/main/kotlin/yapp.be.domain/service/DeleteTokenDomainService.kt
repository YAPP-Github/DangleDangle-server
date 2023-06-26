package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.DeleteTokenUseCase
import yapp.be.domain.port.outbound.TokenCommandHandler

@Service
class DeleteTokenDomainService(
    private val tokenCommandHandler: TokenCommandHandler
) : DeleteTokenUseCase {
    override fun deleteToken(accessToken: String) {
        tokenCommandHandler.deleteToken(accessToken)
    }
}
