package yapp.be.apiapplication.system.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "jwt")
data class JwtConfigProperties(
    val access: TokenProperties,
    val refresh: TokenProperties,
    val authorization: String,
    val refreshToken: String,
) {
    data class TokenProperties(
        val expire: Long,
        val secret: String
    )
}
