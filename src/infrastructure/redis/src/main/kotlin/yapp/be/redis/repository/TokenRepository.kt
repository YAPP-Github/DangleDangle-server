package yapp.be.redis.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.TokenCommandHandler

@Component
class TokenRepository(
    private val redisRepository: RedisRepository,
) : TokenCommandHandler {
    override fun saveToken(accessToken: String, refreshToken: String, expire: Long) {
        redisRepository.setDataExpire(accessToken, refreshToken, expire)
    }

    override fun deleteToken(accessToken: String) {
        redisRepository.deleteData(accessToken)
    }
}
