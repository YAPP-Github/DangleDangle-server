package yapp.be

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {

    runApplication<ApiApplication>(*args)
}
