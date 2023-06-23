package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.properties.JwtConfigProperties
import yapp.be.domain.port.inbound.SaveTokenUseCase
import yapp.be.redis.service.RedisService

@Service
class SaveTokenDomainService(
    private val redisService: RedisService,
    private val jwtProperties: JwtConfigProperties
) : SaveTokenUseCase {
    override fun saveToken(
        accessToken: String,
        refreshToken: String,
    ) {
        redisService.setDataExpire(accessToken, refreshToken, jwtProperties.refresh.expire)
    }
}
