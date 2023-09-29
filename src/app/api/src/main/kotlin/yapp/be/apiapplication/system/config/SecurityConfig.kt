package yapp.be.apiapplication.system.config

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import yapp.be.apiapplication.system.security.handler.AuthenticationSuccessHandler
import yapp.be.apiapplication.auth.service.CustomOAuth2UserService
import yapp.be.apiapplication.system.security.JwtAuthenticationFilter
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.apiapplication.system.security.handler.CustomAccessDeniedHandler
import yapp.be.apiapplication.system.security.handler.CustomAuthenticationEntryPoint
import yapp.be.apiapplication.system.security.handler.FilterExceptionHandler
import yapp.be.apiapplication.system.security.oauth2.ExtraStatefulParameterOAuth2AuthorizationRequestResolver
import yapp.be.domain.auth.port.inbound.CheckTokenUseCase
import yapp.be.model.enums.volunteerActivity.Role

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val oAuth2ClientProperties: OAuth2ClientProperties,
    private val filterExceptionHandler: FilterExceptionHandler,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler,
    private val checkTokenUseCase: CheckTokenUseCase,
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

        http
            .oauth2Login { loginHandler ->
                loginHandler.successHandler(authenticationSuccessHandler)
                loginHandler.userInfoEndpoint {
                    it.userService(customOAuth2UserService)
                }
                loginHandler.authorizationEndpoint {

                    it.authorizationRequestResolver(
                        ExtraStatefulParameterOAuth2AuthorizationRequestResolver(
                            authorizationRequestBaseUri = "/oauth2/authorization",
                            clientRegistrationRepository = clientRegistrationRepository()
                        )
                    )
                }
            }

        http.authorizeHttpRequests {
            it.requestMatchers(
                AntPathRequestMatcher("/v1/shelter/{shelterId}/bookmark"),
                AntPathRequestMatcher("/v1/volunteer/my/**"),
                AntPathRequestMatcher("/v1/shelter/{shelterId}/volunteer-event/{volunteerEventId}/participate"),
                AntPathRequestMatcher("/v1/shelter/{shelterId}/volunteer-event/{volunteerEventId}/withdraw"),
            ).hasAnyAuthority(Role.VOLUNTEER.name)
            it.requestMatchers(
                AntPathRequestMatcher("/v1/shelter/admin/**")
            ).hasAuthority(Role.SHELTER.name)
            it.anyRequest()
                .permitAll()
        }
        http
            .exceptionHandling {
                it.accessDeniedHandler(customAccessDeniedHandler)
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .addFilterBefore(
                JwtAuthenticationFilter(
                    jwtTokenProvider = jwtTokenProvider,
                    filterExceptionHandler = filterExceptionHandler,
                    checkTokenUseCase = checkTokenUseCase,
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

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val mapper = OAuth2ClientPropertiesMapper(oAuth2ClientProperties)
        val clientRegistrations = mapper.asClientRegistrations()
        return InMemoryClientRegistrationRepository(clientRegistrations)
    }
}
