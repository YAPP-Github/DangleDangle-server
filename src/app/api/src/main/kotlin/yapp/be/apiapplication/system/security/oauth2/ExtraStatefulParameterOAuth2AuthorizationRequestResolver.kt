package yapp.be.apiapplication.system.security.oauth2

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator
import org.springframework.security.crypto.keygen.StringKeyGenerator
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.web.util.UrlUtils
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils
import org.springframework.web.util.UriComponentsBuilder
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.exceptions.CustomException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.function.Consumer

class ExtraStatefulParameterOAuth2AuthorizationRequestResolver(
    authorizationRequestBaseUri: String,
    private val clientRegistrationRepository: ClientRegistrationRepository
) : OAuth2AuthorizationRequestResolver {

    private val authorizationRequestMatcher = AntPathRequestMatcher(
        "$authorizationRequestBaseUri/{$REGISTRATION_ID_URI_VARIABLE_NAME}"
    )

    companion object {
        private val REFERER = "Referer"
        private val REGISTRATION_ID_URI_VARIABLE_NAME = "registrationId"

        private val PATH_DELIMITER = '/'
        private val DEFAULT_PKCE_APPLIER = OAuth2AuthorizationRequestCustomizers.withPkce()
        private val authorizationRequestCustomizer = Consumer { customizer: OAuth2AuthorizationRequest.Builder? -> }

        private val DEFAULT_SECURE_KEY_GENERATOR: StringKeyGenerator = Base64StringKeyGenerator(
            Base64.getUrlEncoder().withoutPadding(), 96
        )

        private fun createHash(value: String): String? {
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(value.toByteArray(StandardCharsets.US_ASCII))
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
        }
        private fun applyNonce(builder: OAuth2AuthorizationRequest.Builder) {
            try {
                val nonce = DEFAULT_SECURE_KEY_GENERATOR.generateKey()
                val nonceHash = createHash(nonce)
                builder.attributes { attrs: MutableMap<String?, Any?> -> attrs["nonce"] = nonce }
                builder.additionalParameters { params: MutableMap<String?, Any?> -> params["nonce"] = nonceHash }
            } catch (ex: NoSuchAlgorithmException) {
                ex.printStackTrace()
            }
        }
    }

    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        val clientRegistrationId = resolveRegistrationId(request) ?: return null
        val action = getAction(request, "login") ?: throw CustomException(ApiExceptionType.INTERNAL_SERVER_ERROR, "Can't find Suitable oAuth Action [login]")
        return resolve(
            request = request,
            clientRegistrationId = clientRegistrationId,
            action = action
        )
    }

    override fun resolve(request: HttpServletRequest?, clientRegistrationId: String?): OAuth2AuthorizationRequest? {
        if (clientRegistrationId == null) {
            return null
        }
        val action = getAction(request!!, "authorize")
            ?: throw CustomException(ApiExceptionType.INTERNAL_SERVER_ERROR, "Can't find Suitable oAuth Action [authorize]")
        return resolve(
            request = request,
            clientRegistrationId = clientRegistrationId,
            action = action
        )
    }

    fun resolve(request: HttpServletRequest, clientRegistrationId: String, action: String): OAuth2AuthorizationRequest? {
        val clientRegistration: ClientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId)
            ?: throw CustomException(ApiExceptionType.INTERNAL_SERVER_ERROR, "Invalid Client Registration with Id: $clientRegistrationId")
        val builder: OAuth2AuthorizationRequest.Builder = getBuilder(clientRegistration)

        val host = getClientHost(request)

        val redirectUriStr = expandRedirectUri(
            request = request,
            clientRegistration = clientRegistration,
            action = action
        )

        builder.clientId(clientRegistration.clientId)
            .authorizationUri(clientRegistration.providerDetails.authorizationUri)
            .redirectUri(redirectUriStr)
            .state(host)
            .scopes(clientRegistration.scopes)

        authorizationRequestCustomizer.accept(builder)

        return builder.build()
    }

    private fun getBuilder(clientRegistration: ClientRegistration): OAuth2AuthorizationRequest.Builder {
        if (AuthorizationGrantType.AUTHORIZATION_CODE == clientRegistration.authorizationGrantType) {
            val builder = OAuth2AuthorizationRequest.authorizationCode()
                .attributes { attrs: MutableMap<String, Any> -> attrs[OAuth2ParameterNames.REGISTRATION_ID] = clientRegistration.registrationId }
            if (!CollectionUtils.isEmpty(clientRegistration.scopes) &&
                clientRegistration.scopes.contains(OidcScopes.OPENID)
            ) {
                applyNonce(builder)
            }
            if (ClientAuthenticationMethod.NONE == clientRegistration.clientAuthenticationMethod) {
                DEFAULT_PKCE_APPLIER.accept(builder)
            }
            return builder
        }
        throw IllegalArgumentException(
            "Invalid Authorization Grant Type (${clientRegistration.authorizationGrantType.value} for Client Registration with Id: ${clientRegistration.registrationId}"
        )
    }

    private fun resolveRegistrationId(request: HttpServletRequest): String? {
        if (!authorizationRequestMatcher.matches(request))
            return null

        return authorizationRequestMatcher.matcher(request).variables[REGISTRATION_ID_URI_VARIABLE_NAME]
    }

    private fun getAction(request: HttpServletRequest, defaultAction: String): String? {
        return request.getParameter("action") ?: return defaultAction
    }

    private fun expandRedirectUri(request: HttpServletRequest, clientRegistration: ClientRegistration, action: String): String {
        val uriVariables: MutableMap<String, String> = java.util.HashMap()
        uriVariables["registrationId"] = clientRegistration.registrationId
        val uriComponents = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
            .replacePath(request.contextPath)
            .replaceQuery(null)
            .fragment(null)
            .build()
        val scheme = uriComponents.scheme
        uriVariables["baseScheme"] = scheme ?: ""
        val host = uriComponents.host
        uriVariables["baseHost"] = host ?: ""
        val port = uriComponents.port
        uriVariables["basePort"] = if (port == -1) "" else ":$port"
        var path = uriComponents.path
        if (StringUtils.hasLength(path)) {
            if (path!![0] != PATH_DELIMITER) {
                path = PATH_DELIMITER.toString() + path
            }
        }
        uriVariables["basePath"] = path ?: ""
        uriVariables["baseUrl"] = uriComponents.toUriString()
        uriVariables["action"] = action ?: ""
        return UriComponentsBuilder.fromUriString(clientRegistration.redirectUri).buildAndExpand(uriVariables)
            .toUriString()
    }

    private fun getClientHost(request: HttpServletRequest): String {
        return request.getHeader(REFERER) ?: "http://localhost:3000"
    }
}
