package support.container

import io.kotest.core.listeners.AfterProjectListener
import io.kotest.core.listeners.BeforeProjectListener
import io.kotest.core.spec.AutoScan
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.MountableFile

@AutoScan
@Testcontainers
class MySqlContainer : BeforeProjectListener, AfterProjectListener {

    companion object {
        @Container
        @JvmStatic
        val instance: KGenericContainer =
            KGenericContainer(MySqlContainerProperties.imageName)
                .apply {
                    withEnv("MYSQL_DATABASE", MySqlContainerProperties.DATABASE)
                    withEnv("MYSQL_ROOT_PASSWORD", MySqlContainerProperties.PASSWORD)
                    withExposedPorts(MySqlContainerProperties.PORT)
                    withCopyToContainer(MountableFile.forClasspathResource("sql/1.ddl.sql"), "/docker-entrypoint-initdb.d/1.ddl.sql")
                }
    }

    override suspend fun beforeProject() {
        instance.start()

        System.setProperty("spring.datasource.username", "root")
        System.setProperty("spring.datasource.password", MySqlContainerProperties.PASSWORD)
        System.setProperty("spring.datasource.url", "jdbc:mysql://${instance.host}:${instance.getMappedPort(MySqlContainerProperties.PORT)}/${MySqlContainerProperties.DATABASE}")
    }

    override suspend fun afterProject() {
        instance.stop()
    }
}
class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)
