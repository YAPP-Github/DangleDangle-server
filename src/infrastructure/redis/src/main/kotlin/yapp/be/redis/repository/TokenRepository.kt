package yapp.be.redis.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.TokenCommandHandler
import yapp.be.domain.port.outbound.TokenQueryHandler
import java.time.Duration

@Component
class TokenRepository(
    private val redisHandler: RedisHandler,
) : TokenQueryHandler, TokenCommandHandler {

    override fun getTokensByAuthToken(authToken: String): String? {
        return redisHandler.getData(authToken)
    }

    override fun getTokenByAccessToken(accessToken: String): String? {
        return redisHandler.getData(accessToken)
    }

    override fun saveToken(accessToken: String, refreshToken: String, duration: Duration) {
        redisHandler.setDataExpire(
            key = accessToken,
            value = refreshToken,
            duration = duration
        )
    }

    override fun saveTokensWithAuthToken(authToken: String, accessToken: String, refreshToken: String, duration: Duration) {
        redisHandler.setDataExpire(
            key = authToken,
            value = "$accessToken,$refreshToken",
            duration = duration
        )
    }

    override fun deleteTokenByAuthToken(authToken: String) {
        redisHandler.deleteData(authToken)
    }

    override fun deleteToken(accessToken: String) {
        redisHandler.deleteData(accessToken)
    }
}
