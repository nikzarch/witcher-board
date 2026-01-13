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

@Component
class JWTProvider(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val expirationMs: Long
) {

    private val key: Key
        get() = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    fun extractUsername(token: String): String =
        getClaims(token).subject

    fun isTokenValid(token: String): Boolean =
        try {
            val claims = getClaims(token)
            !claims.expiration.before(Date())
        } catch (ex: Exception) {
            false
        }

    private fun getClaims(token: String): Claims =
        Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()

    fun generateToken(username: String, customExpirationMs: Long? = null): String {
        val now = Date()
        val expiry = Date(now.time + (customExpirationMs ?: expirationMs))
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}
