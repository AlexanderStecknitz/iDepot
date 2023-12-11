package de.stecknitz.backend.core.service

import de.stecknitz.backend.core.domain.DepositAccount
import de.stecknitz.backend.core.domain.Depot
import de.stecknitz.backend.core.repository.DepositAccountRepositoryKotlin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DepositAccountServiceKotlin(private val depositAccountRepository: DepositAccountRepositoryKotlin) {

    fun create(depot: Depot) {
        val depositAccount = DepositAccount.builder()
            .depot(depot)
            .build()
        depositAccountRepository.saveAndFlush(depositAccount)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(DepositAccountServiceKotlin::class.java)
    }
}