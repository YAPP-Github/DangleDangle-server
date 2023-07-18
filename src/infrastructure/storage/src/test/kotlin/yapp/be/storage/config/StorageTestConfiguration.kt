package yapp.be.storage.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.test.context.ContextConfiguration

@SpringBootApplication(scanBasePackages = ["yapp.be.storage"])
@ContextConfiguration(classes = [JpaConfig::class, QueryDslConfig::class])
class StorageTestConfiguration
