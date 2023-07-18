package yapp.be.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.test.context.TestConfiguration
import redis.embedded.RedisServer

@TestConfiguration
class TestRedisConfiguration(
    private val redisProperties: RedisProperties
) {
    lateinit var redisServer: RedisServer
    @PostConstruct
    @Throws(Exception::class)
    fun redisServer() {
        redisServer = RedisServer(redisProperties.port)
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }
}
