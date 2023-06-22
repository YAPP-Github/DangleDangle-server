package yapp.be.apiapplication.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.properties.JwtConfigProperties
import yapp.be.apiapplication.system.security.CustomOAuth2User
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.domain.port.inbound.GetUserUseCase
import yapp.be.exceptions.CustomException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Component
class AuthenticationSuccessHandler(
    @Value("\${oauth.redirect-url}")
    private val REDIRECT_URI: String,
    private val jwtTokenProvider: JwtTokenProvider,
    private val getUserUseCase: GetUserUseCase,
    private val jwtConfigProperties: JwtConfigProperties,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val customOAuth2User = authentication.principal as CustomOAuth2User
        val userEmail = customOAuth2User.oAuthAttributes.email

        try {
            val user = getUserUseCase.getByEmail(userEmail)
            val token = jwtTokenProvider.generate(user.id, user.email, user.role)

            val session = request.getSession(true)
            session.setAttribute(jwtConfigProperties.authorization, token.accessToken)
            session.setAttribute(jwtConfigProperties.refreshToken, token.refreshToken)
            response.status = HttpServletResponse.SC_OK

            val param = "email=" + URLEncoder.encode(userEmail, StandardCharsets.UTF_8)
            redirectStrategy.sendRedirect(request, response, "$REDIRECT_URI?$param")
        } catch (e: CustomException) {
            val param = "isMember=" + false
            redirectStrategy.sendRedirect(request, response, "$REDIRECT_URI?$param")
        }
    }
}
