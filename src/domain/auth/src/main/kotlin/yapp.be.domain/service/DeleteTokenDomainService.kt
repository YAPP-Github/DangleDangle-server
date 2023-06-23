package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.DeleteTokenUseCase
import yapp.be.redis.service.RedisService

@Service
class DeleteTokenDomainService(
    private val redisService: RedisService
) : DeleteTokenUseCase {
    override fun deleteToken(accessToken: String) {
        redisService.deleteData(accessToken)
    }
}
