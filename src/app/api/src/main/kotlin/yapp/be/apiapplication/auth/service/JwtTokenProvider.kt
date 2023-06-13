package yapp.be.apiapplication.auth.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import yapp.be.storage.jpa.user.model.CustomOAuth2User
import yapp.be.storage.jpa.user.model.CustomUserDetails
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.stream.Collectors
import kotlin.Boolean
import kotlin.Exception
import kotlin.IllegalStateException
import kotlin.String
import kotlin.toString

@Component
class JwtTokenProvider(
    @Value("\${app.auth.tokenExpiry}")
    private val ACCESS_TOKEN_EXPIRE_LENGTH: Int,
    @Value("\${app.auth.tokenExpiry}")
    private val REFRESH_TOKEN_EXPIRE_LENGTH: Int,
    @Value("\${jwt.secret}")
    private val SECRET_KEY: String
) {
    private val AUTHORITIES_KEY = "role"
    private val signingKey = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray(StandardCharsets.UTF_8))
    private val log = LoggerFactory.getLogger(javaClass)

    private fun generateToken(email: String, role: String, date: Date): String {
        return Jwts.builder()
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .claim(AUTHORITIES_KEY, role)
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(date)
            .compact()
    }

    private fun generateToken(email: String, date: Date): String {
        return Jwts.builder()
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(date)
            .compact()
    }

    fun createAccessToken(authentication: Authentication): String {
        val now = Date()
        val validity = Date(now.time + ACCESS_TOKEN_EXPIRE_LENGTH)
        val user = authentication.principal as CustomOAuth2User
        val userEmail = user.oAuthAttributes.email
        val role = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        return generateToken(userEmail, role, validity)
    }

    fun createRefreshToken(authentication: Authentication): String {
        val now = Date()
        val validity = Date(now.time + REFRESH_TOKEN_EXPIRE_LENGTH)
        val user = authentication.principal as CustomOAuth2User
        val userEmail = user.oAuthAttributes.email
        return generateToken(userEmail, validity)
    }

    private fun parseClaims(token: String): Claims? {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: SecurityException) {
            throw SecurityException("Invalid JWT signature")
        } catch (e: Exception) {
            throw Exception("Invalid JWT token")
        }
    }

    fun validate(token: String): Boolean {
        try {
            parseClaims(token)
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
        val claims = parseClaims(accessToken!!)
        val authorities: Collection<GrantedAuthority?> = Arrays.stream(claims!![AUTHORITIES_KEY].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .map { role: String? -> SimpleGrantedAuthority(role) }.collect(Collectors.toList())
        val principal = CustomUserDetails(null, claims.subject, authorities, null)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }
}
