package de.stecknitz.backend.web.resources.dto

import java.time.Instant

data class TransactionDTOKotlin(
    val created: Instant,
    val stockName: String,
    val buyPrice: Float,
    val amount: Int,
    val investmentValue: Double,
    val type: String,
)
