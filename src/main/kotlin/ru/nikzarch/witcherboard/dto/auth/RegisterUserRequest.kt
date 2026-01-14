package ru.nikzarch.witcherboard.dto.auth

import ru.nikzarch.witcherboard.domain.user.UserRole

data class RegisterUserRequest (
    val username: String,
    val password: String,
    val role: UserRole = UserRole.PEASANT
)
