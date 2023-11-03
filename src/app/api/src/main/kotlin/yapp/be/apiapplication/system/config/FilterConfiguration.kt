package yapp.be.apiapplication.system.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter
import yapp.be.apiapplication.system.filter.servlet.WrappedHttpServletFilter

@Configuration
class FilterConfiguration {

    @Bean
    fun wrappedHttpServletFilter(): FilterRegistrationBean<WrappedHttpServletFilter> {
        val registration = FilterRegistrationBean(WrappedHttpServletFilter())
        registration.order = Ordered.LOWEST_PRECEDENCE
        registration.addUrlPatterns("/*")

        return registration
    }

    @Bean
    fun setResourceUrlEncodingFilter(): FilterRegistrationBean<ResourceUrlEncodingFilter> {
        val registration: FilterRegistrationBean<ResourceUrlEncodingFilter> = FilterRegistrationBean(ResourceUrlEncodingFilter())
        registration.order = Ordered.LOWEST_PRECEDENCE - 1
        registration.addUrlPatterns("/*")
        return registration
    }
}
