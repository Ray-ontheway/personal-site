package top.rayc.personalsite.user.security.provider

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import top.rayc.personalsite.user.security.authentication.UsernamePasswordAuthentication
import top.rayc.personalsite.user.service.impl.UserService

@Component
class UsernamePasswordAuthenticationProvider(
    @Autowired val userService: UserService
): AuthenticationProvider {
    override fun authenticate(authentication: Authentication): UsernamePasswordAuthentication {
        val username = authentication.principal as String
        val password = authentication.credentials as String
        return userService.auth(username, password)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        if (authentication == null) return false
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}