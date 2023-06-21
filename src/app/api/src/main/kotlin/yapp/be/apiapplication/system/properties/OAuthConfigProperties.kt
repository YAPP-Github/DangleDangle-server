package yapp.be.apiapplication.system.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "spring.security.oauth2.client.registration.kakao")
data class OAuthConfigPropertiesProvider(
    val clientId: String,
    val clientSecret: String,
    val clientName: String,
    val redirectUri: String,
)

@ConfigurationProperties(value = "spring.security.oauth2.client.provider.kakao")
data class OAuthConfigProperties(
    val authorizationUri: String,
    val tokenUri: String,
    val userInfoUri: String,
    val userNameAttribute: String,
)
