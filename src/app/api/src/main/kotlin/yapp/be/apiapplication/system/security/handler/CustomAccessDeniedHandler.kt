package yapp.be.apiapplication.system.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.exception.ApiExceptionType

@Component
class CustomAccessDeniedHandler(
    private val filterExceptionHandler: FilterExceptionHandler
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: org.springframework.security.access.AccessDeniedException?
    ) {
        filterExceptionHandler.sendErrorMessage(
            res = response,
            exceptionCode = ApiExceptionType.UNAUTHORIZED_EXCEPTION.code,
            message = "접근 권한이 없습니다.",
            httpStatus = HttpServletResponse.SC_UNAUTHORIZED
        )
    }
}
