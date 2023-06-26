package yapp.be.apiapplication.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.security.CustomOAuth2User
import yapp.be.apiapplication.system.security.JwtConfigProperties
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.apiapplication.system.security.SecurityTokenType
import yapp.be.apiapplication.system.security.util.CookieUtil
import yapp.be.domain.port.inbound.GetVolunteerUseCase
import yapp.be.domain.port.inbound.SaveTokenUseCase
import yapp.be.exceptions.CustomException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Component
class AuthenticationSuccessHandler(
    @Value("\${oauth.redirect-url}")
    private val REDIRECT_URI: String,
    private val jwtTokenProvider: JwtTokenProvider,
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
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
            val user = getVolunteerUseCase.getByEmail(userEmail)
            val token = jwtTokenProvider.generate(user.id, user.email, user.role)

            saveTokenUseCase.saveToken(token.accessToken, token.refreshToken, jwtConfigProperties.refresh.expire)
            CookieUtil.addHttpOnlyCookie(SecurityTokenType.ACCESS.value, token.accessToken, jwtConfigProperties.access.expire.toInt())
            CookieUtil.addHttpOnlyCookie(SecurityTokenType.REFRESH.value, token.refreshToken, jwtConfigProperties.refresh.expire.toInt())
            redirectStrategy.sendRedirect(request, response, "$REDIRECT_URI")
        } catch (e: CustomException) {
            val param = "email=" + URLEncoder.encode(userEmail, StandardCharsets.UTF_8) +
                "&isMember=" + false
            redirectStrategy.sendRedirect(request, response, "$REDIRECT_URI?$param")
        }
    }
}
