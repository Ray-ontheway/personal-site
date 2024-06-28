package top.rayc.personalsite.utility.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.Authentication
import java.util.Date

object JwtTokenUtil {
    fun generateJwtToken(
        jwtSigningKey: String,
        authentication: Authentication,
        jwtDuration: Long
    ): String {
        val signedAt = System.currentTimeMillis()
        val expireAt = signedAt + jwtDuration

        val claims = Jwts.claims().apply {
            put("role", authentication.authorities.map { it.authority })
            subject = authentication.principal as String
            issuedAt = Date(signedAt)
            expiration = Date(expireAt)
        }
        val key = Keys.hmacShaKeyFor(jwtSigningKey.toByteArray())
        return Jwts.builder()
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getClaimsFromToken(token: String, jwtSigningKey: String): Claims {
        return runCatching {
            val key = Keys.hmacShaKeyFor(jwtSigningKey.toByteArray())
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        }.getOrElse { throw RuntimeException("invalid token") }
    }

    fun validateToken(token: String, jwtSigningKey: String): Boolean {
        return runCatching {
            val claims = getClaimsFromToken(token, jwtSigningKey)
            claims.expiration != null && claims.expiration.after(Date())
        }.onFailure {
            it.printStackTrace()
        }.getOrElse { false }
    }
}