package de.stecknitz.backend.web.resources.dto

import de.stecknitz.backend.core.domain.StockKotlin

data class StockDTOKotlin(
    var isin: String,
    var symbol: String,
    var name: String,
    var wkn: String,
    var currentPrice: Float
) {
    companion object {

        fun toStock(stockDTO: StockDTOKotlin): StockKotlin = StockKotlin(
            isin = stockDTO.isin,
            symbol = stockDTO.symbol,
            wkn = stockDTO.wkn,
            name = stockDTO.name,
            currentPrice = stockDTO.currentPrice
        )

    }
}