package yapp.be.utils

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * Cookie 접근 위한 공통 클래스
 */
object CookieUtil {
    private val currentRequest: HttpServletRequest
        get() = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
    private val currentResponse: HttpServletResponse
        get() = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).response
            ?: throw IllegalStateException("HttpServletResponse 가 존재하지 않습니다.")


    /**
     * 현재 응답에 HttpOnly=true 쿠키 추가.
     */
    fun addHttpOnlyCookie(name: String, value: String, expiredSeconds: Int) {
        currentResponse.addCookie(
            Cookie(name, value).apply {
                path = "/"
                isHttpOnly = true
                maxAge = expiredSeconds
            }
        )
    }
}
