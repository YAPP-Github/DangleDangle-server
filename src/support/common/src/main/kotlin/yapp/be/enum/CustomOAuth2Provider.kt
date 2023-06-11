package yapp.be.enum

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

enum class CustomOAuth2Provider {

    KAKAO {
        @Override
        override fun getBuilder(registrationId: String): ClientRegistration.Builder {
            val builder: ClientRegistration.Builder = getBuilder(registrationId, ClientAuthenticationMethod.CLIENT_SECRET_POST, DEFAULT_LOGIN_REDIRECT_URL)
            builder.scope("account_email")
            builder.authorizationUri("https://kauth.kakao.com/oauth/authorize")
            builder.tokenUri("https://kauth.kakao.com/oauth/token")
            builder.userInfoUri("https://kapi.kakao.com/v2/user/me")
            builder.userNameAttributeName("id")
            builder.clientName("Kakao")
            return builder
        }
    };

    val DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}"

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
    abstract fun getBuilder(registrationId: String): ClientRegistration.Builder
}
