package yapp.be.apiapplication.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.apiapplication.system.security.SecurityTokenType
import yapp.be.apiapplication.system.security.oauth2.CustomOAuth2User
import yapp.be.apiapplication.system.security.properties.JwtConfigProperties
import yapp.be.domain.port.inbound.*
import yapp.be.model.vo.Email
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Duration

@Component
class AuthenticationSuccessHandler(
    private val jwtTokenProvider: JwtTokenProvider,
    private val saveOAuthNonMemberInfoUseCase: SaveOAuthNonMemberInfoUseCase,
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val checkVolunteerUseCase: CheckVolunteerUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val jwtConfigProperties: JwtConfigProperties,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val customOAuth2User = authentication.principal as CustomOAuth2User
        val userEmail = Email(customOAuth2User.customOAuthAttributes.email)
        val isMember = checkVolunteerUseCase.isExistByEmail(userEmail)
        val REDIRECT_URI = request.getParameter("client_redirect_uri")

        if (isMember) {
            val user = getVolunteerUseCase.getByEmail(userEmail)
            val authToken = jwtTokenProvider.generateToken(
                id = user.id,
                email = user.email,
                role = user.role,
                securityTokenType = SecurityTokenType.AUTH
            )

            val accessToken = jwtTokenProvider.generateToken(
                id = user.id,
                email = user.email,
                role = user.role,
                securityTokenType = SecurityTokenType.ACCESS
            )
            val refreshToken = jwtTokenProvider.generateToken(
                id = user.id,
                email = user.email,
                role = user.role,
                securityTokenType = SecurityTokenType.REFRESH
            )

            saveTokenUseCase.saveTokensWithAuthToken(
                authToken = authToken,
                accessToken = accessToken,
                refreshToken = refreshToken,
                authTokenExpire = Duration.ofMinutes(jwtConfigProperties.auth.expire / (1000 * 60L))
            )

            val param = "authToken=" + URLEncoder.encode(authToken, StandardCharsets.UTF_8)
            redirectStrategy.sendRedirect(request, response, "$REDIRECT_URI?$param")
        } else {
            saveOAuthNonMemberInfoUseCase.saveOAuthNonMemberInfo(
                email = userEmail,
                oAuthIdentifier = customOAuth2User.name,
                duration = Duration.ofMinutes(5)
            )
            val param = "email=" + URLEncoder.encode(userEmail.value, StandardCharsets.UTF_8) +
                "&isMember=" + false
            redirectStrategy.sendRedirect(request, response, "$REDIRECT_URI?$param")
        }
    }
}
