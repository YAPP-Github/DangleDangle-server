package container

import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.spec.AutoScan
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
@AutoScan
@Testcontainers
class RedisContainer : BeforeProjectListener, AfterProjectListener {

    companion object {
        @Container
        @JvmStatic
        private val instance: KGenericContainer =
            KGenericContainer(RedisContainerProperties.imageName)
                .apply {
                    withExposedPorts(RedisContainerProperties.PORT)
                }
    }

    override suspend fun beforeProject() {
        instance.start()

        System.setProperty("REDIS_HOST", "127.0.0.1")
        System.setProperty("REDIS_PORT", "${instance.getMappedPort(RedisContainerProperties.PORT)}")
    }

    override suspend fun afterProject() {
        instance.stop()
    }
}
class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)
