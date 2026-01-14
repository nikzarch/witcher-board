package ru.nikzarch.witcherboard.filter

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

import mu.KotlinLogging
import ru.nikzarch.witcherboard.domain.user.UserRole

private val logger = KotlinLogging.logger{}

@Component
class JWTProvider(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val expirationMs: Long
) {

    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    fun extractUsername(token: String): String =
        getClaims(token).subject
    fun extractRole(token: String): UserRole =
        UserRole.valueOf(getClaims(token).getValue("role").toString())
    fun isTokenValid(token: String): Boolean =
        try {
            val claims = getClaims(token)
            !claims.expiration.before(Date())
        } catch (ex: Exception) {
            logger.warn { "Invalid JWT: ${ex.message}" }
            false
        }

    private fun getClaims(token: String): Claims =
        Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getPayload()

    fun generateToken(username: String, role: UserRole, customExpirationMs: Long? = null): String {
        val now = Date()
        val expiry = Date(now.time + (customExpirationMs ?: expirationMs))
        return Jwts.builder()
            .setSubject(username)
            .claim("role",role.name)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}
