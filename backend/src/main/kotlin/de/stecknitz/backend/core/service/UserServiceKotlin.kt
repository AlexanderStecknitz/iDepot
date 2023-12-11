package de.stecknitz.backend.core.service

import de.stecknitz.backend.core.domain.Role
import de.stecknitz.backend.core.domain.UserKotlin
import de.stecknitz.backend.core.repository.UserRepositoryKotlin
import de.stecknitz.backend.core.service.exception.UserAlreadyExistsException
import de.stecknitz.backend.core.service.exception.UserNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.random.Random

@Service
class UserServiceKotlin(
    private val userRepository: UserRepositoryKotlin,
    private val passwordEncoder: PasswordEncoder,
    private val depotServiceKotlin: DepotServiceKotlin
) {

    fun create(user: UserKotlin) {
        val optionalUser: Optional<UserKotlin> = userRepository.findByEmail(user.email)
        if (optionalUser.isPresent) {
            throw UserAlreadyExistsException()
        }
        val salt: String = generateSalt()
        user.salt = salt
        user.password = passwordEncoder.encode(user.password + salt)
        user.role = Role.builder()
            .name("USER")
            .build()
        userRepository.save(user)
        depotServiceKotlin.createByUser(user)
    }

    fun findByEmail(email: String): UserKotlin {
        val optionalUser: Optional<UserKotlin> = userRepository.findByEmail(email)
        if (optionalUser.isEmpty) {
            throw UserNotFoundException()
        }
        return optionalUser.get()
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun generateSalt(): String {
        val salt: ByteArray = ByteArray(16)
        val random: Random = Random.Default
        random.nextBytes(salt)
        return Base64.encode(salt)
    }

}