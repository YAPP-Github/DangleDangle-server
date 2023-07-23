package yapp.be.redis.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.outbound.TokenCommandHandler
import yapp.be.domain.port.outbound.TokenQueryHandler
import java.time.Duration

@Component
class TokenRepository(
    private val redisHandler: RedisHandler,
) : TokenQueryHandler, TokenCommandHandler {

    @Transactional(readOnly = true)
    override fun getTokensByAuthToken(authToken: String): String? {
        return redisHandler.getData(authToken)
    }

    @Transactional(readOnly = true)
    override fun getTokenByAccessToken(accessToken: String): String? {
        return redisHandler.getData(accessToken)
    }

    @Transactional
    override fun saveToken(accessToken: String, refreshToken: String, duration: Duration) {
        redisHandler.setDataExpire(
            key = accessToken,
            value = refreshToken,
            duration = duration
        )
    }

    @Transactional
    override fun saveLogoutToken(accessToken: String, value: String, duration: Duration) {
        redisHandler.setDataExpire(
            key = accessToken,
            value = value,
            duration = duration
        )
    }

    @Transactional
    override fun saveTokensWithAuthToken(authToken: String, accessToken: String, refreshToken: String, duration: Duration) {
        redisHandler.setDataExpire(
            key = authToken,
            value = "$accessToken,$refreshToken",
            duration = duration
        )
    }

    @Transactional
    override fun deleteTokenByAuthToken(authToken: String) {
        redisHandler.deleteData(authToken)
    }

    @Transactional
    override fun deleteToken(accessToken: String) {
        redisHandler.deleteData(accessToken)
    }
}
