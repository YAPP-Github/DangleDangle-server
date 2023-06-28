package yapp.be.apiapplication.system.security.resolver

import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import yapp.be.apiapplication.system.security.CustomUserDetails
import yapp.be.model.Email

@Configuration
class ShelterUserAuthenticationInfoArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(ShelterUserAuthentication::class.java) != null &&
            parameter.parameterType == ShelterUserAuthenticationInfo::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val authenticationToken = SecurityContextHolder.getContext().authentication
        val principal = authenticationToken.principal as CustomUserDetails
        return ShelterUserAuthenticationInfo(
            shelterUserId = principal.id!!,
            email = Email(principal.username)

        )
    }
}
