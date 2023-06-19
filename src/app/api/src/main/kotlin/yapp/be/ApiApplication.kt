package yapp.be

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableJpaAuditing
class ApiApplication

fun main(args: Array<String>) {

    runApplication<ApiApplication>(*args)
}
