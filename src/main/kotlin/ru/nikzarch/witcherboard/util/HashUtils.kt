package ru.nikzarch.witcherboard.util

import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64

@Component
class HashUtils {
    fun generateSalt(): ByteArray {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        return salt
    }

    fun hashPassword(password: String): HashResult {
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            val salt = generateSalt()
            md.update(salt)
            val hashedPassword = md.digest(password.toByteArray())
            HashResult(bytesToHex(hashedPassword), Base64.getEncoder().encodeToString(salt))
        } catch (exc: NoSuchAlgorithmException) {
            throw RuntimeException("Error hashing password", exc)
        }
    }

    fun hashPassword(password: String, salt: ByteArray): HashResult {
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update(salt)
            val hashedPassword = md.digest(password.toByteArray())
            HashResult(bytesToHex(hashedPassword), Base64.getEncoder().encodeToString(salt))
        } catch (exc: NoSuchAlgorithmException) {
            throw RuntimeException("Error hashing password", exc)
        }
    }

    private fun bytesToHex(bytes: ByteArray): String =
        bytes.joinToString("") { "%02x".format(it) }
}

data class HashResult(val hashed: String, val salt: String)