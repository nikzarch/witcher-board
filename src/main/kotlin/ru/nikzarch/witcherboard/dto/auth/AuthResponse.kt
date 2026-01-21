package ru.nikzarch.witcherboard.dto.auth

import ru.nikzarch.witcherboard.domain.user.UserRole

data class AuthResponse(
    val token: String,
    val role: UserRole,
    val id: Long
)