package de.stecknitz.backend.core.repository

import de.stecknitz.backend.core.domain.DepositAccountTransaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DepositAccountTransactionRepositoryKotlin : JpaRepository<DepositAccountTransaction, Long> {

    fun findByDepositAccountId(depositAccountId: Long): Optional<List<DepositAccountTransaction>>

}