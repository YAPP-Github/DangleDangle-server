package yapp.be.apiapplication.system.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.apiapplication.system.security.handler.CustomAccessDeniedHandler
import yapp.be.apiapplication.system.security.handler.CustomAuthenticationEntryPoint
import yapp.be.apiapplication.system.security.handler.FilterExceptionHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import yapp.be.apiapplication.auth.service.CustomOAuth2UserService
import yapp.be.apiapplication.system.security.CustomOAuth2Provider
import yapp.be.apiapplication.auth.handler.AuthenticationSuccessHandler
import yapp.be.apiapplication.system.properties.OAuthConfigProperties
import yapp.be.apiapplication.system.properties.OAuthConfigPropertiesProvider
import yapp.be.apiapplication.system.security.JwtAuthenticationFilter
import yapp.be.enum.OAuthType
import yapp.be.enum.Role

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val jwtTokenProvider: JwtTokenProvider,
    private val filterExceptionHandler: FilterExceptionHandler,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors {}
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        http
            .oauth2Login { it ->
                it.successHandler(authenticationSuccessHandler)
                it.userInfoEndpoint {
                    it.userService(customOAuth2UserService)
                }
            }

        http.authorizeHttpRequests {
            it.requestMatchers(
                AntPathRequestMatcher("/v1/auth/shelter/**"),
                AntPathRequestMatcher("/oauth/authorize/**"),
                AntPathRequestMatcher("/v1/user/**"),
                AntPathRequestMatcher("/v1/auth/**")
            ).permitAll()
            it.requestMatchers(
                AntPathRequestMatcher("/v1/shelter/admin/**")
            ).hasRole(Role.SHELTER.name)
        }

        http
            .exceptionHandling {
                it.accessDeniedHandler(customAccessDeniedHandler)
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .addFilterBefore(
                JwtAuthenticationFilter(
                    objectMapper = objectMapper,
                    jwtTokenProvider = jwtTokenProvider,
                    filterExceptionHandler = filterExceptionHandler
                ),
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            it.ignoring().requestMatchers(
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger/**"
            )
        }
    }

    fun clientRegistrationRepository(
        oAuthConfigProperties: OAuthConfigProperties,
        oAuthConfigPropertiesProvider: OAuthConfigPropertiesProvider
    ): ClientRegistrationRepository {
        val registrations: MutableList<ClientRegistration> = mutableListOf()
        registrations.add(
            CustomOAuth2Provider.KAKAO.getBuilder(OAuthType.KAKAO.oAuthType, oAuthConfigPropertiesProvider, oAuthConfigProperties)
                .clientId(oAuthConfigPropertiesProvider.clientId)
                .clientSecret(oAuthConfigPropertiesProvider.clientSecret)
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
