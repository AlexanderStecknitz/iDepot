package de.stecknitz.backend.core.service

import de.stecknitz.backend.core.domain.Depot
import de.stecknitz.backend.core.domain.UserKotlin
import de.stecknitz.backend.core.repository.DepotRepositoryKotlin
import de.stecknitz.backend.core.repository.UserRepositoryKotlin
import de.stecknitz.backend.core.service.exception.UserNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DepotServiceKotlin(
    private val depotRepository: DepotRepositoryKotlin,
    private val userRepository: UserRepositoryKotlin,
    private val depositAccountService: DepositAccountServiceKotlin
) {

    @Transactional(readOnly = true)
    fun findAllDepots(): List<Depot> = depotRepository.findAll()

    @Transactional(readOnly = true)
    fun findAllByEmail(email: String): List<Depot> = depotRepository.findAllByUserEmail(email)

    @Transactional(readOnly = true)
    fun findById(id: Long): Depot? = depotRepository.findById(id).orElse(null)
    fun createByUser(user: UserKotlin) {
        val depot: Depot = Depot.builder()
            .user(user)
            .build()
        depotRepository.saveAndFlush(depot)
    }

    @Transactional
    fun create(email: String) {
        val user: Optional<UserKotlin> = userRepository.findByEmail(email)
        if (user.isEmpty) {
            throw UserNotFoundException()
        }
        val depot: Depot = Depot.builder()
            .user(user.get())
            .build()

        val depotWithId: Depot = depotRepository.saveAndFlush(depot)
        depositAccountService.create(depotWithId)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(DepotServiceKotlin::class.java)
    }

}