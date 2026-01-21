package ru.nikzarch.witcherboard.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

interface SecurityService {
    fun isOwner(userId: Long) : Boolean
    fun getAuthenticatedUser() : UserDetails
}