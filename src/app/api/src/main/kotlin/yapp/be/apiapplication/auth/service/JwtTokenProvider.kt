package yapp.be.apiapplication.auth.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import yapp.be.apiapplication.system.properties.JwtConfigProperties
import yapp.be.storage.jpa.user.model.CustomOAuth2User
import yapp.be.storage.jpa.user.model.CustomUserDetails
import java.util.*
import javax.crypto.SecretKey
import kotlin.Boolean
import kotlin.Exception
import kotlin.IllegalStateException
import kotlin.String

@Component
class JwtTokenProvider(
    val jwtTokenProperties: JwtConfigProperties,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private fun generateToken(email: String, date: Date, key: SecretKey): String {
        return Jwts.builder()
            .claim("email", email)
            .setIssuedAt(Date())
            .setExpiration(date)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun createAccessToken(authentication: Authentication): String {
        val now = Date()
        val validity = Date(now.time + jwtTokenProperties.access.expire)
        val user = authentication.principal as CustomOAuth2User
        val userEmail = user.oAuthAttributes.email
        val key : SecretKey = Keys.hmacShaKeyFor(jwtTokenProperties.access.secret.toByteArray())
        return generateToken(userEmail, validity, key)
    }

    fun createRefreshToken(authentication: Authentication): String {
        val now = Date()
        val validity = Date(now.time + jwtTokenProperties.refresh.expire)
        val user = authentication.principal as CustomOAuth2User
        val userEmail = user.oAuthAttributes.email
        val key : SecretKey = Keys.hmacShaKeyFor(jwtTokenProperties.refresh.secret.toByteArray())
        return generateToken(userEmail, validity, key)
    }

    private fun parseClaims(token: String, tokenType: String): Claims {
        try {
            val key: SecretKey = when(tokenType) {
                "ACCESS" -> {
                    Keys.hmacShaKeyFor(jwtTokenProperties.access.secret.toByteArray())
                }
                "REFRESH" ->{
                    Keys.hmacShaKeyFor(jwtTokenProperties.refresh.secret.toByteArray())
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
