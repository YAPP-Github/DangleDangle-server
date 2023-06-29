package yapp.be.redis.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.TokenCommandHandler
import yapp.be.domain.port.outbound.TokenQueryHandler

@Component
class TokenRepository(
    private val redisRepository: RedisRepository,
) : TokenQueryHandler, TokenCommandHandler {

    override fun getTokensByAuthToken(authToken: String): String? {
        return redisRepository.getData(authToken)
    }
    override fun saveToken(accessToken: String, refreshToken: String, expire: Long) {
        redisRepository.setDataExpire(
            key = accessToken,
            value = refreshToken,
            duration = expire
        )
    }

    override fun saveTokensWithAuthToken(authToken: String, accessToken: String, refreshToken: String, expire: Long) {
        redisRepository.setDataExpire(
            key = authToken,
            value = "$accessToken,$refreshToken",
            duration = expire
        )
    }

    override fun deleteTokenByAuthToken(authToken: String) {
        redisRepository.deleteData(authToken)
    }

    override fun deleteToken(accessToken: String) {
        redisRepository.deleteData(accessToken)
    }
}
