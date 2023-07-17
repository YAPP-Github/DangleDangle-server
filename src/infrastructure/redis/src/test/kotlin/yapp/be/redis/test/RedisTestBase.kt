package yapp.be.redis.test

import io.kotest.core.spec.style.StringSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RedisTestBase : StringSpec({
    "Redis 연결테스트" {
    }
})
