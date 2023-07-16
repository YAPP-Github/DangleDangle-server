package support.configuration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.test.context.ContextConfiguration
import yapp.be.storage.config.JpaConfig
import yapp.be.storage.config.QueryDslConfig

@SpringBootApplication(scanBasePackages = ["yapp.be.storage"])
@ContextConfiguration(classes = [JpaConfig::class, QueryDslConfig::class])
class StorageTestConfiguration
