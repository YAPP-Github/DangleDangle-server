package yapp.be.apiapplication.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import yapp.be.apiapplication.system.security.CustomOAuth2User
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.domain.port.inbound.GetVolunteerUseCase
import yapp.be.exceptions.CustomException
import java.nio.charset.StandardCharsets

@Component
class AuthenticationSuccessHandler(
    @Value("\${oauth.redirect-url}")
    private val REDIRECT_URI: String,
    private val jwtTokenProvider: JwtTokenProvider,
    private val getVolunteerUseCase: GetVolunteerUseCase,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val customOAuth2User = authentication.principal as CustomOAuth2User
        val userEmail = customOAuth2User.oAuthAttributes.email

        try {
            val user = getVolunteerUseCase.getByEmail(userEmail)
            val token = jwtTokenProvider.generate(user.id, user.email, user.role)

            response.sendRedirect(
                UriComponentsBuilder.fromUriString(REDIRECT_URI)
                    .queryParam("accessToken", token.accessToken)
                    .queryParam("refreshToken", token.refreshToken)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString()
            )
        } catch (e: CustomException) {
            response.sendRedirect(
                UriComponentsBuilder.fromUriString(REDIRECT_URI)
                    .queryParam("isMember", false)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString()
            )
        }
    }
}
