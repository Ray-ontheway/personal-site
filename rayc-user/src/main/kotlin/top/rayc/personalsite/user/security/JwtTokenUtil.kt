package top.rayc.personalsite.user.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.Authentication
import top.rayc.personalsite.user.entity.User
import top.rayc.personalsite.utility.logger.LoggerDelegate
import java.util.Date
import kotlin.jvm.Throws

object JwtTokenUtil {
    val log by LoggerDelegate()

    fun generateJwtToken(
        jwtSigningKey: String,
        authentication: Authentication,
        jwtDuration: Long
    ): String {
        val signedAt = System.currentTimeMillis()
        val expireAt = signedAt + jwtDuration

        val claims = mapOf(
            "sub" to authentication.principal as String,
            "role" to authentication.authorities.map { it.authority },
            "userId" to (authentication.details as User).id
        )

        val key = Keys.hmacShaKeyFor(jwtSigningKey.toByteArray())
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date(signedAt))
            .setExpiration(Date(expireAt))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    @Throws(JwtException::class)
    fun getClaimsFromToken(token: String, jwtSigningKey: String): Claims {
        return runCatching {
            val key = Keys.hmacShaKeyFor(jwtSigningKey.toByteArray())
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        }.getOrElse { throw JwtException("invalid token") }
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