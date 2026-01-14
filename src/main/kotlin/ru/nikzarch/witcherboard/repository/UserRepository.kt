package ru.nikzarch.witcherboard.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.nikzarch.witcherboard.domain.user.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}