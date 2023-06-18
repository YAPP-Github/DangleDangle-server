package yapp.be.apiapplication.system.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import yapp.be.apiapplication.system.security.ShelterUserAuthenticationInfoArgumentResolver

@Configuration
class WebConfig(
    private val shelterUserAuthenticationInfoArgumentResolver: ShelterUserAuthenticationInfoArgumentResolver
) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedHeaders("*")
            .allowedMethods("*")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(shelterUserAuthenticationInfoArgumentResolver)
    }
}
