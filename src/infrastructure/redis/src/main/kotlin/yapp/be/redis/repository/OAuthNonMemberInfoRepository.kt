package yapp.be.redis.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.OAuthNonMemberInfoCommandHandler
import yapp.be.domain.port.outbound.OAuthNonMemberInfoQueryHandler
import yapp.be.model.vo.Email
import java.time.Duration

@Component
class OAuthNonMemberInfoRepository(
    private val redisHandler: RedisHandler
) : OAuthNonMemberInfoQueryHandler, OAuthNonMemberInfoCommandHandler {

    override fun get(email: Email): String? {
        return redisHandler.getData(email.value)
    }

    override fun save(email: Email, oAuthIdentifier: String, duration: Duration) {
        redisHandler.setDataExpire(
            key = email.value,
            value = oAuthIdentifier,
            duration = duration
        )
    }
}
