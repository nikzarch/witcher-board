package ru.nikzarch.witcherboard.service.impl

import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.nikzarch.witcherboard.domain.user.User
import ru.nikzarch.witcherboard.domain.user.UserRole
import ru.nikzarch.witcherboard.dto.auth.AuthResponse
import ru.nikzarch.witcherboard.dto.auth.LoginUserRequest
import ru.nikzarch.witcherboard.dto.auth.RegisterUserRequest
import ru.nikzarch.witcherboard.exception.InvalidCredentialsException
import ru.nikzarch.witcherboard.filter.JWTProvider
import ru.nikzarch.witcherboard.repository.UserRepository
import ru.nikzarch.witcherboard.service.UserService
import ru.nikzarch.witcherboard.util.HashUtils
import java.util.*

private val logger = KotlinLogging.logger {}


@Service
@RequiredArgsConstructor
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val hashUtils: HashUtils,
    private val jwtProvider: JWTProvider
) : UserDetailsService, UserService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: run {
                logger.error { "User with name $username not found" }
                throw UsernameNotFoundException("User not found")
            }

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.username)
            .password(user.password)
            .roles(user.role.name)
            .disabled(!user.enabled)
            .build()
    }

    override fun findUserByName(name: String) = userRepository.findByUsername(name)

    override fun findUserById(id: Long) = userRepository.findById(id).orElse(null)

    override fun registerUser(request: RegisterUserRequest): AuthResponse {
        if (userRepository.findByUsername(request.username) != null) {
            throw RuntimeException("User already exists")
        }
        if (request.role.equals(UserRole.GOD)) {
            throw IllegalArgumentException("u cant be god")
        }
        val hashResult = hashUtils.hashPassword(request.password)
        val user = User(
            username = request.username,
            password = hashResult.hashed,
            salt = hashResult.salt,
            role = request.role
        )
        userRepository.save(user)

        val token: String = jwtProvider.generateToken(user.username, user.role)
        return AuthResponse(token)
    }

    override fun loginUser(request: LoginUserRequest): AuthResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw UsernameNotFoundException("user not found")

        val saltBytes = Base64.getDecoder().decode(user.salt)

        val hashResult = hashUtils.hashPassword(request.password, saltBytes)

        if (hashResult.hashed != user.password) {
            throw InvalidCredentialsException("Invalid username or password")
        }

        val token = jwtProvider.generateToken(user.username, user.role)
        return AuthResponse(token)
    }

    @Transactional
    override fun changeBalance(userId: Long, delta: Long) {
        val user = userRepository.findById(userId).orElseThrow { throw UsernameNotFoundException("user not found") }
        val newBalance = user.balance + delta
        if (newBalance < 0) {
            throw IllegalStateException("balance cant be less than zero")
        }
        userRepository.save(user)
    }
}