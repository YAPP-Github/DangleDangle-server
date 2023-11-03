package yapp.be.apiapplication.system.filter.servlet

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.filter.OncePerRequestFilter
import yapp.be.apiapplication.system.utils.injectAccessLog

class WrappedHttpServletFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val wrappedRequest = WrappedHttpServletRequest(request)
        val wrappedResponse = WrappedHttpServletResponse(response)

        injectAccessLog(wrappedRequest)
        filterChain.doFilter(wrappedRequest, wrappedResponse)
        MDC.clear()
    }
}
