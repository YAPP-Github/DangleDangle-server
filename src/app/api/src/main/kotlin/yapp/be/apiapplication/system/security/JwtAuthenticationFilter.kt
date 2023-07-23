package yapp.be.apiapplication.system.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import yapp.be.apiapplication.system.security.handler.FilterExceptionHandler
import yapp.be.exceptions.CustomException

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val filterExceptionHandler: FilterExceptionHandler
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        try {
            val token = (request as HttpServletRequest).getHeader("Authorization")

            if (token != null) {
                val claims = jwtTokenProvider.parseClaims(token.replace("Bearer ", ""), SecurityTokenType.ACCESS)
                if (claims != null) {
                    SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthentication(claims)
                }
            }
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            filterExceptionHandler.sendErrorMessage(
                res = response as HttpServletResponse,
                exceptionCode = e.type.code,
                message = e.message,
                httpStatus = HttpServletResponse.SC_UNAUTHORIZED
            )
        }
    }
}
