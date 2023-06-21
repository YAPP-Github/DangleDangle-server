package yapp.be.apiapplication.system.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "spring.security.oauth2.client.registration.kakao")
data class OAuthConfigProperties(
    val clientId: String,
    val clientSecret: String,
)
