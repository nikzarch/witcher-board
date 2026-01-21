package ru.nikzarch.witcherboard.service.impl

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import ru.nikzarch.witcherboard.service.SecurityService

@Component("securityService")
class SecurityServiceImpl : SecurityService {
    override fun isOwner(userId: Long): Boolean{
        val auth = SecurityContextHolder.getContext().authentication
        val principal = auth.principal as UserDetails

        if (auth.authorities.any { it.authority == "ROLE_GOD" }) {
            return true
        }

        return principal.username == userId.toString()
    }
    override fun getAuthenticatedUser() : UserDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
}