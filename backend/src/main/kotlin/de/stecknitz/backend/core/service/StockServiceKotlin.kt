package de.stecknitz.backend.core.service

import de.stecknitz.backend.core.domain.StockKotlin
import de.stecknitz.backend.core.repository.StockRepositoryKotlin
import de.stecknitz.backend.core.service.exception.StockAlreadyExistsException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StockServiceKotlin(private val stockRepository: StockRepositoryKotlin) {

    @Transactional(readOnly = true)
    fun findAll(): List<StockKotlin> = stockRepository.findAll()

    @Transactional(readOnly = true)
    fun findById(isin: String): StockKotlin? = stockRepository.findById(isin).orElse(null)

    @Transactional
    fun create(stock: StockKotlin): StockKotlin {
        val optionalStock: Optional<StockKotlin> = stockRepository.findById(stock.isin)
        if (optionalStock.isPresent) {
            throw StockAlreadyExistsException()
        }
        return stockRepository.saveAndFlush(stock)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(StockServiceKotlin::class.java)
    }
}