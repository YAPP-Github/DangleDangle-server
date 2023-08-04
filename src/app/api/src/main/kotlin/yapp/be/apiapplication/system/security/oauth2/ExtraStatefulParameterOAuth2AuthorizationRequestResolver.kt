package yapp.be.apiapplication.system.security.oauth2

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

class ExtraStatefulParameterOAuth2AuthorizationRequestResolver(
    clientRegistrationRepository: ClientRegistrationRepository
) : OAuth2AuthorizationRequestResolver {

    val REFERER = "Referer"
    val CLIENT_REDIRECT_URI = "client_redirect_uri"
    var defaultOAuth2AuthorizationRequestResolver: OAuth2AuthorizationRequestResolver

    init {
        this.defaultOAuth2AuthorizationRequestResolver = DefaultOAuth2AuthorizationRequestResolver(
            clientRegistrationRepository, OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
        )
    }
    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        val authorizationRequest = defaultOAuth2AuthorizationRequestResolver.resolve(request)
        val clientRedirectHost = getClientHost(request)

        if (authorizationRequest != null) {

            val extraStatefulParameters: MutableMap<String, Any> = HashMap(authorizationRequest.additionalParameters)
            extraStatefulParameters[CLIENT_REDIRECT_URI] = "$clientRedirectHost/volunteer/redirect"

            val newRequest = OAuth2AuthorizationRequest.from(authorizationRequest)
                .additionalParameters(extraStatefulParameters)
                .build()

            return newRequest
        }
        return null
    }

    override fun resolve(request: HttpServletRequest, clientRegistrationId: String): OAuth2AuthorizationRequest? {
        return defaultOAuth2AuthorizationRequestResolver.resolve(request, clientRegistrationId)
    }

    private fun getClientHost(request: HttpServletRequest): String {
        return request.getHeader(REFERER) ?: "localhost:8080"
    }
}
