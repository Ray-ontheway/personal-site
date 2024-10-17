package top.rayc.personalsite.user.security

import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtil {
    fun getCurrentUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            return authentication.details as Long
        }
        return -1L
    }
}