package de.stecknitz.backend.core.repository

import de.stecknitz.backend.core.domain.UserKotlin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepositoryKotlin : JpaRepository<UserKotlin, String> {
    fun findByEmail(email: String): Optional<UserKotlin>

}