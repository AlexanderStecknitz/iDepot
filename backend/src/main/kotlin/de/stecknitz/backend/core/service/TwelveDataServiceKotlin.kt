package de.stecknitz.backend.core.service

import de.stecknitz.backend.core.domain.StockKotlin
import de.stecknitz.backend.core.service.client.twelvedata.TwelveDataClientKotlin
import de.stecknitz.backend.core.service.client.twelvedata.dto.EndOfDayDTOKotlin
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TwelveDataServiceKotlin(
    private val twelveDataClient: TwelveDataClientKotlin,
    private val stockServiceKotlin: StockServiceKotlin
) {

    @Transactional
    fun getEndOfDayData(): EndOfDayDTOKotlin? {
        val stocks: List<StockKotlin> = stockServiceKotlin.findAll()
        val endOfDayDataDTO = twelveDataClient.getEndOfDay(stocks[0].symbol)
        stocks[0].currentPrice = endOfDayDataDTO!!.close.toFloat()
        stockServiceKotlin.create(stocks[0])
        return endOfDayDataDTO
    }

}