package yapp.be.apiapplication.system.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.DecodingException
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
import yapp.be.model.enums.volunteerActivity.Role
import yapp.be.exceptions.CustomException
import yapp.be.model.vo.Email

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
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "유효하지 않은 토큰입니다.")
        } catch (e: ExpiredJwtException) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "만료된 토큰입니다. 다시 로그인을 진행해주세요.")
        } catch (e: DecodingException) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "잘못된 인증입니다.")
        } catch (e: MalformedJwtException) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "손상된 토큰입니다.")
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
