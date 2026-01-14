package ru.nikzarch.witcherboard.dto.auth

data class LoginUserRequest (
    val username: String,
    val password: String
)