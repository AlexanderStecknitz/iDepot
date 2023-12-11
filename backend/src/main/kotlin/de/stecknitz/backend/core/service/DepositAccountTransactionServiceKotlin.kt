package de.stecknitz.backend.core.service

import de.stecknitz.backend.core.domain.DepositAccountTransaction
import de.stecknitz.backend.core.repository.DepositAccountTransactionRepositoryKotlin
import org.springframework.stereotype.Service

@Service
class DepositAccountTransactionServiceKotlin(private val depositAccountTransactionRepository: DepositAccountTransactionRepositoryKotlin) {

    fun findByDepositAccountId(depositAccountId: Long): List<DepositAccountTransaction> =
        depositAccountTransactionRepository.findByDepositAccountId(depositAccountId).orElse(
            emptyList()
        )

}