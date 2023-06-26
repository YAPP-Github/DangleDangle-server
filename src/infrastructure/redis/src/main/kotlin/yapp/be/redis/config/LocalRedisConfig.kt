package yapp.be.redis.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
@Configuration
@Profile("local", "dev")
class LocalRedisConfig(
    @Value("\${spring.cache.redis.port}")
    private val redisPort: Int,
    private var redisServer: RedisServer?
) {
    @PostConstruct
    fun redisServer() {
        redisServer = RedisServer(redisPort)
        redisServer!!.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer?.stop()
    }
}
