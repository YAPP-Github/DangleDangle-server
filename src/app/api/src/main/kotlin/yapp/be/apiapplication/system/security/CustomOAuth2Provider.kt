package yapp.be.apiapplication.system.security

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import yapp.be.apiapplication.auth.properties.OAuthConfigProperties
import yapp.be.apiapplication.auth.properties.OAuthConfigPropertiesProvider

enum class CustomOAuth2Provider {

    KAKAO {
        @Override
        override fun getBuilder(registrationId: String, provider: OAuthConfigPropertiesProvider, properties: OAuthConfigProperties): ClientRegistration.Builder {
            val builder: ClientRegistration.Builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_POST, provider.redirectUri)
            builder.scope("account_email")
            builder.authorizationUri(properties.authorizationUri)
            builder.tokenUri(properties.tokenUri)
            builder.userInfoUri(properties.userInfoUri)
            builder.userNameAttributeName(properties.userNameAttribute)
            builder.clientName(provider.clientName)
            return builder
        }
    };

    protected fun getBuilder(
        registrationId: String,
        method: ClientAuthenticationMethod?,
        redirectUri: String?
    ): ClientRegistration.Builder {
        val builder = ClientRegistration.withRegistrationId(registrationId)
        builder.clientAuthenticationMethod(method)
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        builder.redirectUri(redirectUri)
        return builder
    }
    abstract fun getBuilder(registrationId: String, provider: OAuthConfigPropertiesProvider, properties: OAuthConfigProperties): ClientRegistration.Builder
}
