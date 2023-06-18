package yapp.be.apiapplication.system.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import java.util.Collections
import java.util.Date
import javax.crypto.SecretKey
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.domain.model.SecurityToken
import yapp.be.enum.Role
import yapp.be.exceptions.CustomException

@Component
class JwtTokenProvider(
    val jwtConfigProperties: JwtConfigProperties,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private fun generateToken(id: Long, role: Role, securityTokenType: SecurityTokenType): String {
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
            .setId(id.toString())
            .claim("role", role.name)
            .setIssuedAt(Date(now))
            .setExpiration(Date(expiredAt))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generate(id: Long, role: Role): SecurityToken {
        val accessToken = generateToken(id, role, SecurityTokenType.ACCESS)
        val refreshToken = generateToken(id, role, SecurityTokenType.REFRESH)
        return SecurityToken(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    private fun parseClaims(token: String, tokenType: SecurityTokenType): Claims {
        try {
            val key: SecretKey = when (tokenType) {
                SecurityTokenType.ACCESS -> {
                    Keys.hmacShaKeyFor(jwtConfigProperties.access.secret.toByteArray())
                }
                SecurityTokenType.REFRESH -> {
                    Keys.hmacShaKeyFor(jwtConfigProperties.refresh.secret.toByteArray())
                }
            }
            return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .body
        } catch (e: SecurityException) {
            throw SecurityException("Invalid JWT signature")
        } catch (e: Exception) {
            throw Exception("Invalid JWT token")
        }
    }

    fun validate(token: String, tokenType: SecurityTokenType): Boolean {
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

    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken, SecurityTokenType.ACCESS)
        val id = claims.id.toLong()
        val role = claims["role"] as? String ?: throw CustomException(ApiExceptionType.UNAUTHENTICATED_EXCEPTION, "Cannot Find Role")
        val principal = CustomUserDetails(
            id = id,
            email = claims.subject,
            authorities = Collections.singletonList(SimpleGrantedAuthority("ROLE_$role")),
            attributes = null
        )
        return UsernamePasswordAuthenticationToken(principal, "", null)
    }
}
