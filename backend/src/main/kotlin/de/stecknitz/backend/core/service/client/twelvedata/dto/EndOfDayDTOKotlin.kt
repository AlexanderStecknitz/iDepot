package de.stecknitz.backend.core.service.client.twelvedata.dto

data class EndOfDayDTOKotlin(
    val symbol: String,
    val exchange: String?,
    val mic_code: String?,
    val currency: String?,
    val datetime: String?,
    val close: String,
)