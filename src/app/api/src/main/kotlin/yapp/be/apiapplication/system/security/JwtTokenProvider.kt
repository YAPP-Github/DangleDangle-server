package yapp.be.apiapplication.system.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.Collections
import java.util.Date
import javax.crypto.SecretKey
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.apiapplication.system.security.properties.JwtConfigProperties
import yapp.be.enum.Role
import yapp.be.exceptions.CustomException
import yapp.be.model.Email

@Component
class JwtTokenProvider(
    val jwtConfigProperties: JwtConfigProperties,
) {

    fun generateToken(id: Long, email: Email, role: Role, securityTokenType: SecurityTokenType): String {
        val properties = when (securityTokenType) {
            SecurityTokenType.ACCESS -> {
                jwtConfigProperties.access
            }

            SecurityTokenType.REFRESH -> {
                jwtConfigProperties.refresh
            }

            SecurityTokenType.AUTH -> {
                jwtConfigProperties.auth
            }
        }
        val key: SecretKey = Keys.hmacShaKeyFor(properties.secret.toByteArray())
        val now = Date().time
        val expiredAt = now + properties.expire

        return Jwts.builder()
            .claim("id", id)
            .claim("role", role.name)
            .claim("email", email.value)
            .setIssuedAt(Date(now))
            .setExpiration(Date(expiredAt))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun parseClaims(token: String, tokenType: SecurityTokenType): Claims? {
        try {
            val key: SecretKey = when (tokenType) {
                SecurityTokenType.ACCESS -> {
                    Keys.hmacShaKeyFor(jwtConfigProperties.access.secret.toByteArray())
                }
                SecurityTokenType.REFRESH -> {
                    Keys.hmacShaKeyFor(jwtConfigProperties.refresh.secret.toByteArray())
                }
                SecurityTokenType.AUTH -> {
                    Keys.hmacShaKeyFor(jwtConfigProperties.auth.secret.toByteArray())
                }
            }
            return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .body
        } catch (e: SecurityException) {
            throw SecurityException("Invalid JWT signature")
        }
    }

    fun getAuthentication(claims: Claims): Authentication {
        val id = claims["id"].toString().toLong()
        val email = claims["email"].toString()
        val role = claims["role"] as? String ?: throw CustomException(ApiExceptionType.UNAUTHENTICATED_EXCEPTION, "Cannot Find Role")
        val authorities = Collections.singletonList(SimpleGrantedAuthority(role))
        val principal = CustomUserDetails(
            id = id,
            email = email,
            authorities = authorities,
            attributes = null
        )
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }
}
