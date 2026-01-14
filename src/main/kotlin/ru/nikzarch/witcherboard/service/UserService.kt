package ru.nikzarch.witcherboard.service

import ru.nikzarch.witcherboard.domain.user.User
import ru.nikzarch.witcherboard.dto.auth.AuthResponse
import ru.nikzarch.witcherboard.dto.auth.LoginUserRequest
import ru.nikzarch.witcherboard.dto.auth.RegisterUserRequest


interface UserService {
    fun findUserByName(name: String): User?
    fun findUserById(id: Long): User?
    fun registerUser(request: RegisterUserRequest): AuthResponse
    fun loginUser(request: LoginUserRequest): AuthResponse
    fun changeBalance(userId: Long, delta: Long)
}