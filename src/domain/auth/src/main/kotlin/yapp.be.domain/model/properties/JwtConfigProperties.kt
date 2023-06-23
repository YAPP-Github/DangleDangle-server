package yapp.be.domain.model.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "jwt")
data class JwtConfigProperties(
    val access: TokenProperties,
    val refresh: TokenProperties,
) {
    data class TokenProperties(
        val expire: Long,
        val secret: String
    )
}
