package ru.nikzarch.witcherboard.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.nikzarch.witcherboard.domain.user.User

@Repository
interface UserRepository : JpaRepository<User,Long> {
    fun findByUsername(Username: String): User?
}