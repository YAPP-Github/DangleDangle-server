package yapp.be.apiapplication.auth.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.properties.JwtConfigProperties
import yapp.be.domain.model.SecurityToken
import yapp.be.domain.model.SecurityTokenType
import yapp.be.storage.jpa.user.model.CustomUserDetails
import java.util.*
import javax.crypto.SecretKey
import kotlin.Boolean
import kotlin.Exception
import kotlin.IllegalStateException
import kotlin.String

@Component
class JwtTokenProvider(
    val jwtConfigProperties: JwtConfigProperties,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private fun generateToken(id: Long, securityTokenType: SecurityTokenType): String {
        val properties = when (securityTokenType) {
            SecurityTokenType.ACCESS -> {
                jwtConfigProperties.access
            }

            SecurityTokenType.REFRESH -> {
                jwtConfigProperties.refresh
            }
        }
        val key: SecretKey = Keys.hmacShaKeyFor(properties.secret.toByteArray())
        val now = Date().time
        val expiredAt = now + properties.expire

        return Jwts.builder()
            .claim("id", id)
            .setIssuedAt(Date(now))
            .setExpiration(Date(expiredAt))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generate(id: Long): SecurityToken {
        val accessToken = generateToken(id, SecurityTokenType.ACCESS)
        val refreshToken = generateToken(id, SecurityTokenType.REFRESH)
        return SecurityToken(
            userId = id,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    private fun parseClaims(token: String, tokenType: String): Claims {
        try {
            val key: SecretKey = when (tokenType) {
                "ACCESS" -> {
                    Keys.hmacShaKeyFor(jwtConfigProperties.access.secret.toByteArray())
                }
                "REFRESH" -> {
                    Keys.hmacShaKeyFor(jwtConfigProperties.refresh.secret.toByteArray())
                }
                else -> {
                    throw Exception("Invalid token type")
                }
            }
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: SecurityException) {
            throw SecurityException("Invalid JWT signature")
        } catch (e: Exception) {
            throw Exception("Invalid JWT token")
        }
    }

    fun validate(token: String, tokenType: String): Boolean {
        try {
            parseClaims(token, tokenType)
            return true
        } catch (e: ExpiredJwtException) {
            log.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            log.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalStateException) {
            log.info("JWT 토큰이 잘못되었습니다")
        }
        return false
    }

    fun getAuthentication(accessToken: String?): Authentication {
        val claims = parseClaims(accessToken!!, "ACCESS")
        val principal = CustomUserDetails(null, claims.subject, null, null)
        return UsernamePasswordAuthenticationToken(principal, "", null)
    }
}
