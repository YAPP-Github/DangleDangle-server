package yapp.be

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import yapp.be.config.TestRedisConfiguration

@SpringBootTest(
    properties = ["spring.config.location=classpath:application-test.yml"],
    classes = [TestRedisConfiguration::class]
)
class ApiApplicationTests {

    @Test
    fun contextLoads() {
    }
}
