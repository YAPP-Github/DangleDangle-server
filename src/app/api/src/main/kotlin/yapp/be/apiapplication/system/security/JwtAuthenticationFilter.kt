package yapp.be.apiapplication.system.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.apiapplication.system.security.handler.FilterExceptionHandler

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
                    SecurityContextHolder.getContext().authentication =
                        jwtTokenProvider.getAuthentication(claims)
                }
            }
            filterChain.doFilter(request, response)
        } catch (e: SignatureException) {
            filterExceptionHandler.sendErrorMessage(
                res = response as HttpServletResponse,
                exceptionCode = ApiExceptionType.UNAUTHORIZED_EXCEPTION.code,
                message = "유효하지 않은 토큰입니다.",
                httpStatus = HttpServletResponse.SC_UNAUTHORIZED
            )
        } catch (e: MalformedJwtException) {

            filterExceptionHandler.sendErrorMessage(
                res = response as HttpServletResponse,
                exceptionCode = ApiExceptionType.UNAUTHORIZED_EXCEPTION.code,
                message = "손상된 토큰입니다.",
                httpStatus = HttpServletResponse.SC_UNAUTHORIZED
            )
        } catch (e: DecodingException) {
            filterExceptionHandler.sendErrorMessage(
                res = response as HttpServletResponse,
                exceptionCode = ApiExceptionType.UNAUTHORIZED_EXCEPTION.code,
                message = "잘못된 인증입니다.",
                httpStatus = HttpServletResponse.SC_UNAUTHORIZED
            )
        } catch (e: ExpiredJwtException) {
            filterExceptionHandler.sendErrorMessage(
                res = response as HttpServletResponse,
                exceptionCode = ApiExceptionType.UNAUTHORIZED_EXCEPTION.code,
                message = "만료된 토큰입니다.",
                httpStatus = HttpServletResponse.SC_UNAUTHORIZED
            )
        }
    }
}
