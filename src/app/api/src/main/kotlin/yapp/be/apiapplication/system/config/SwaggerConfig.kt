package yapp.be.apiapplication.system.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthenticationInfo
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo

@Configuration
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER,
    paramName = "Authorization"
)
class SwaggerConfig {

    init {
        val ignoreTypes = setOf<Class<*>>(ShelterUserAuthenticationInfo::class.java, VolunteerAuthenticationInfo::class.java).toTypedArray()
        SpringDocUtils.getConfig().addRequestWrapperToIgnore(*ignoreTypes)
    }
    @Bean
    fun customOpenAPI(): OpenAPI {
        val securityRequirement = SecurityRequirement().addList("BearerAuth")

        return OpenAPI()
            .addServersItem(Server().url("/"))
            .components(Components())
            .info(
                Info().title("Yapp22-BE-Api")
            )
            .addSecurityItem(securityRequirement)
    }
}
