package yapp.be.apiapplication.system.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.exception.ApiExceptionType

@Component
class CustomAuthenticationEntryPoint(
    private val filterExceptionHandler: FilterExceptionHandler
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        filterExceptionHandler.sendErrorMessage(
            res = response,
            exceptionCode = ApiExceptionType.UNAUTHENTICATED_EXCEPTION.code,
            message = "접근 권한이 없습니다.",
            httpStatus = HttpServletResponse.SC_FORBIDDEN
        )
    }
}
