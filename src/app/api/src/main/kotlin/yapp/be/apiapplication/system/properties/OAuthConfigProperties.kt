package yapp.be.apiapplication.system.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class OAuthConfigProperties(
    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    val kakaoClientId: String,
    @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}")
    val kakaoClientSecret: String,
)
