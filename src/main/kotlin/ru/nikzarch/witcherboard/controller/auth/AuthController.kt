package ru.nikzarch.witcherboard.controller.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import ru.nikzarch.witcherboard.dto.ErrorResponse
import ru.nikzarch.witcherboard.dto.auth.AuthResponse
import ru.nikzarch.witcherboard.dto.auth.LoginUserRequest
import ru.nikzarch.witcherboard.dto.auth.RegisterUserRequest
import ru.nikzarch.witcherboard.service.UserService
import java.time.Instant

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterUserRequest) : ResponseEntity<AuthResponse>{
        return ResponseEntity.ok(userService.registerUser(request))
    }
    @PostMapping("/login")
    fun login(@RequestBody request: LoginUserRequest) : ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(userService.loginUser(request))
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(
        exc: Exception
    ): ResponseEntity<*>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(exc.message.toString(), Instant.now()))
    }

}