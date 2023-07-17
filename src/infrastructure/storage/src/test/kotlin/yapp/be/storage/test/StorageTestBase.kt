package yapp.be.storage.test

import io.kotest.core.spec.style.StringSpec
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [StorageTestConfiguration::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(properties = ["spring.config.location=classpath:application-test.yml"])
class StorageTestBase : StringSpec({

    "DDL과 Entity 일치여부 validation 테스트" {
    }
})
