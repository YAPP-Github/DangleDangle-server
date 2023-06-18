package yapp.be.apiapplication.system.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import yapp.be.apiapplication.system.security.JwtAuthenticationFilter
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.apiapplication.system.security.handler.CustomAccessDeniedHandler
import yapp.be.apiapplication.system.security.handler.CustomAuthenticationEntryPoint
import yapp.be.apiapplication.system.security.handler.FilterExceptionHandler
import yapp.be.enum.Role

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val jwtTokenProvider: JwtTokenProvider,
    private val filterExceptionHandler: FilterExceptionHandler,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
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

        http.authorizeHttpRequests {
            it.requestMatchers(
                AntPathRequestMatcher("/v1/auth/shelter/**")
            ).permitAll()
            it.requestMatchers(
                AntPathRequestMatcher("/v1/shelter/**", HttpMethod.POST.name()),
                AntPathRequestMatcher("/v1/shelter/**", HttpMethod.PUT.name()),
                AntPathRequestMatcher("/v1/shelter/**", HttpMethod.DELETE.name())
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
}
