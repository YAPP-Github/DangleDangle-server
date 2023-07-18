package yapp.be.redis.test

import io.kotest.core.spec.style.BehaviorSpec
import yapp.be.redis.config.RedisTest

@RedisTest
class RedisConnectionTest : BehaviorSpec({
    Given("applicaiton.yml 설정 정보를 읽어") {
        When("Redis TestContainer가 동작하고 있다면") {
            Then("연결 되어야 한다.") {
            }
        }
    }
})
