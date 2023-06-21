package yapp.be.apiapplication.system.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "jwt")
data class JwtConfigProperties(
    val access: TokenProperties,
    val refresh: TokenProperties
) {
    data class TokenProperties(
        val expire: Long,
        val secret: String
    )
}
