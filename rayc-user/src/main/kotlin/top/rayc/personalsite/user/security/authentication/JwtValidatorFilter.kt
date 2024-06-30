package top.rayc.personalsite.user.security.authentication

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import top.rayc.personalsite.utility.logger.LoggerDelegate
import top.rayc.personalsite.utility.utils.JwtTokenUtil

@Component
class JwtValidatorFilter: OncePerRequestFilter() {
    private val TOKEN_HEADER_KEY = "Authorization"
    private val LOGIN_PATH = "/login"
    private val AUTHORIZATION_PREFIX = "Bearer "

    val log by LoggerDelegate()

    @Value("\${jwt.signing.key:ymLTU8rq83j4fmJZj60wh4OrMNuntIj4fmJ}")
    lateinit var jwtSigningKey: String

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val claims = try {
            JwtTokenUtil.getClaimsFromToken(response.getHeader(TOKEN_HEADER_KEY).substring(7), jwtSigningKey)
        } catch (e: Exception) {
            null
        }
        claims?.apply {
            val username = claims.subject
            val authorities = claims.get("role", List::class.java).map { SimpleGrantedAuthority(it as String) }.toList()

            val auth = UsernamePasswordAuthentication(username, null, authorities)
            SecurityContextHolder.getContext().authentication = auth
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        log.error("request.path: ${request.servletPath}")
        val bearerToken =  request.getHeader(TOKEN_HEADER_KEY) ?: return true
        if (bearerToken.isBlank() || bearerToken.startsWith(AUTHORIZATION_PREFIX)) {
            return true
        }
        val servletPath = request.servletPath
        if (servletPath.contains("druid") || servletPath.contains("doc.html") || LOGIN_PATH == servletPath) {
            return true
        }
        return JwtTokenUtil.validateToken(bearerToken.substring(7), jwtSigningKey)

    }
}