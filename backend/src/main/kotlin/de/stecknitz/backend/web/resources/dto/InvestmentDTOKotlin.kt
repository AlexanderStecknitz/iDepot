package de.stecknitz.backend.web.resources.dto

data class InvestmentDTOKotlin(
    val isin: String,
    val name: String,
    val depotId: Long,
    val amount: Float,
    val currentPrice: Float,
    val buyPrice: Float,
    var yield: Float,
    val transactionType: String
)
