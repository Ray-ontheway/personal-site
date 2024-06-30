package top.rayc.personalsite.user.security.authentication

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class UsernamePasswordAuthentication(
    principal: Any,
    credentials: Any?,
    authorities: Collection<out GrantedAuthority>? = null
): UsernamePasswordAuthenticationToken(
    principal, credentials, authorities
) {
}