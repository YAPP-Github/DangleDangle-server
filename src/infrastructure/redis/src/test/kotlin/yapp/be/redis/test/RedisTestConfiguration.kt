package yapp.be.redis.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.test.context.ContextConfiguration
import yapp.be.redis.config.RedisConfig

@ContextConfiguration(classes = [RedisConfig::class])
@SpringBootApplication(scanBasePackages = ["yapp.be.redis"])
class RedisTestConfiguration
