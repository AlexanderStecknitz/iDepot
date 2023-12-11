package de.stecknitz.backend.web.resources.dto

import de.stecknitz.backend.core.domain.DepositAccountTransactionType

data class DepositAccountTransactionDTOKotlin(
    val id: Long,
    val amount: Long,
    val type: DepositAccountTransactionType,
    val created_at: String,
)
