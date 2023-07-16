package container

import configuration.RedisTestConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(properties = ["spring.config.location=classpath:application-test.yml"])
@Transactional
@Testcontainers
@ContextConfiguration(classes = [RedisTestConfiguration::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RedisContainerTestBase {

    companion object {
        @Container
        @JvmStatic
        private val instance: KGenericContainer =
            KGenericContainer(RedisContainerProperties.imageName)
                .apply {
                    withExposedPorts(RedisContainerProperties.PORT)
                }
    }
}
class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)
