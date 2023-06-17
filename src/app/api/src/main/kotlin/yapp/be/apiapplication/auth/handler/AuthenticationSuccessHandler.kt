package yapp.be.apiapplication.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import yapp.be.apiapplication.auth.service.JwtTokenProvider
import yapp.be.domain.service.GetUserService
import yapp.be.exceptions.CustomException
import yapp.be.storage.jpa.user.model.CustomOAuth2User
import java.nio.charset.StandardCharsets

@Component
class AuthenticationSuccessHandler(
    val jwtTokenProvider: JwtTokenProvider,
    private val filterExceptionHandler: FilterExceptionHandler,
    private val getUserService: GetUserService,
) : AuthenticationSuccessHandler {

    companion object {
        const val REDIRECT_URI = "http://localhost:3000/logincheck"
    }
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val customOAuth2User = authentication.principal as CustomOAuth2User
        val userEmail = customOAuth2User.oAuthAttributes.email

        try {
            val user = getUserService.getByEmail(userEmail)
            val token = jwtTokenProvider.generate(user.id)

            response.sendRedirect(
                UriComponentsBuilder.fromUriString(REDIRECT_URI)
                    .queryParam("accessToken", token.accessToken)
                    .queryParam("refreshToken", token.refreshToken)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString()
            )
        } catch (e: CustomException) {
            filterExceptionHandler.sendErrorMessage(response, e.type.code, "존재하지 않는 유저입니다.")
        }
    }
}
