package yapp.be.apiapplication.system.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException
import java.time.LocalDateTime
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.apiapplication.system.exception.ErrorResponse
import yapp.be.apiapplication.system.security.handler.FilterExceptionHandler

class JwtAuthenticationFilter(
    private val objectMapper: ObjectMapper,
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
            sendErrorMessage(response as HttpServletResponse, "유효하지 않은 토큰입니다.")
        } catch (e: MalformedJwtException) {
            sendErrorMessage(response as HttpServletResponse, "손상된 토큰입니다.")
        } catch (e: DecodingException) {
            sendErrorMessage(response as HttpServletResponse, "잘못된 인증입니다.")
        } catch (e: ExpiredJwtException) {
            sendErrorMessage(response as HttpServletResponse, "만료된 토큰입니다.")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun sendErrorMessage(res: HttpServletResponse, message: String) {
        res.status = HttpServletResponse.SC_UNAUTHORIZED
        res.contentType = MediaType.APPLICATION_JSON.toString()
        res.characterEncoding = "UTF-8"
        res.writer.write(
            objectMapper.writeValueAsString(
                ErrorResponse(
                    code = ApiExceptionType.UNAUTHENTICATED_EXCEPTION.code,
                    message = message,
                    timeStamp = LocalDateTime.now()
                )
            )
        )
    }
}
