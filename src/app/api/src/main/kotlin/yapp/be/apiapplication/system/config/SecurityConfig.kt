package yapp.be.apiapplication.system.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import yapp.be.apiapplication.auth.service.CustomOAuth2UserService
import yapp.be.apiapplication.auth.handler.AuthenticationSuccessHandler
import yapp.be.enum.CustomOAuth2Provider

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors {}
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers("/**")
                    .permitAll()
            }
            .oauth2Login { it ->
                it.successHandler(authenticationSuccessHandler)
                it.userInfoEndpoint {
                    it.userService(customOAuth2UserService)
                }
            }

        return http.build()
    }

    @Bean
    fun clientRegistrationRepository(
        @Value("\${spring.security.oauth2.client.registration.kakao.client-id}") kakaoClientId: String,
        @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}") kakaoClientSecret: String
    ): ClientRegistrationRepository {
        val registrations: MutableList<ClientRegistration> = mutableListOf()
        registrations.add(
            CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp")
                .build()
        )
        return InMemoryClientRegistrationRepository(registrations)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedOrigin("http://localhost:3000")
        configuration.addAllowedMethod("*")
        configuration.addAllowedHeader("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
