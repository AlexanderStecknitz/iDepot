package de.stecknitz.backend.core.service

import de.stecknitz.backend.core.domain.Depot
import de.stecknitz.backend.core.domain.Investment
import de.stecknitz.backend.core.domain.StockKotlin
import de.stecknitz.backend.core.repository.InvestmentRepositoryKotlin
import de.stecknitz.backend.core.service.exception.DepotNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InvestmentServiceKotlin(
    private val investmentRepository: InvestmentRepositoryKotlin,
    private val depotServiceKotlin: DepotServiceKotlin,
    private val stockServiceKotlin: StockServiceKotlin
) {

    @Transactional(readOnly = true)
    fun findAll(): List<Investment> = investmentRepository.findAll()

    @Transactional(readOnly = true)
    fun findByDepotId(depotId: Long): List<Investment> = investmentRepository.findByDepotId(depotId) ?: emptyList()

    @Transactional
    fun create(investment: Investment): Investment {
        log.debug("InvestmentService={}", investment)
        val depot: Depot = depotServiceKotlin.findById(investment.depot.id) ?: throw DepotNotFoundException()
        investment.depot = depot
        val stock: StockKotlin? = stockServiceKotlin.findById(investment.stock.isin)
        if (stock == null) {
            stockServiceKotlin.create(investment.stock)
            investment.stock = investment.stock
        } else {
            investment.stock = stock
        }
        return investmentRepository.saveAndFlush(investment)
    }

    @Transactional(readOnly = true)
    fun accumulateInvestmentValue(depotId: Long): Double {
        val investments = findByDepotId(depotId)
        return investments.sumOf { it.investmentValue }
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(InvestmentServiceKotlin::class.java)
    }
}