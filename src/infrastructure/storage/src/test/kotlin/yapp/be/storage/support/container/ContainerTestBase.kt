package yapp.be.storage.support.container

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.MountableFile
import yapp.be.storage.support.configuration.StorageTestConfiguration

@SpringBootTest(properties = ["spring.config.location=classpath:application-test.yml"])
@Transactional
@Testcontainers
@ContextConfiguration(classes = [StorageTestConfiguration::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContainerTestBase {

    companion object {
        @Container
        @JvmStatic
        private val instance: KGenericContainer =
            KGenericContainer(ContainerProperties.imageName)
                .apply {
                    withEnv("MYSQL_DATABASE", ContainerProperties.DATABASE)
                    withEnv("MYSQL_ROOT_PASSWORD", ContainerProperties.PASSWORD)
                    withExposedPorts(ContainerProperties.PORT)
                    withCopyToContainer(MountableFile.forClasspathResource("sql/1.ddl.sql"), "/docker-entrypoint-initdb.d/1.ddl.sql")
                }

        @JvmStatic
        @DynamicPropertySource
        fun registerMySqlProperty(registry: DynamicPropertyRegistry) {

            val host = instance.host
            val port = instance.getMappedPort(ContainerProperties.PORT)
            registry.add("spring.datasource.url") { "jdbc:mysql://$host:$port/${ContainerProperties.DATABASE}" }
            registry.add("spring.datasource.password") { ContainerProperties.PASSWORD }
            registry.add("spring.datasource.username") { "root" }
        }
    }
}
class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)
