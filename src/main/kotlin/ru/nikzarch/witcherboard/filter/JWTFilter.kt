package ru.nikzarch.witcherboard.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

import mu.KotlinLogging
import ru.nikzarch.witcherboard.service.impl.UserServiceImpl

private val ktLogger = KotlinLogging.logger {}

@Component
class JWTFilter(
    private val jwtProvider: JWTProvider,
    private val userDetailsService: UserServiceImpl
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            request.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }?.let { authHeader ->
                val token = authHeader.removePrefix("Bearer ").trim()
                if (jwtProvider.isTokenValid(token)) {
                    val username = jwtProvider.extractUsername(token)
                    val userDetails = userDetailsService.loadUserByUsername(username)
                    SecurityContextHolder.getContext().authentication =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                }
            }

        } catch (ex: Exception) {
            ktLogger.error("Cant set user authentication", ex)
        }

        filterChain.doFilter(request, response)
    }
}
