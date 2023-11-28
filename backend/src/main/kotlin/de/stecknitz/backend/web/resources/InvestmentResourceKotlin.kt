package de.stecknitz.backend.web.resources

import de.stecknitz.backend.core.domain.Investment
import de.stecknitz.backend.core.service.InvestmentService
import de.stecknitz.backend.web.resources.dto.InvestmentDTO
import de.stecknitz.backend.web.resources.dto.TransactionDTO
import de.stecknitz.backend.web.resources.dto.mapper.InvestmentMapper
import de.stecknitz.backend.web.resources.dto.mapper.TransactionMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/investment")
class InvestmentResourceKotlin(
    private val investmentService: InvestmentService,
    private val investmentMapper: InvestmentMapper,
    private val transactionMapper: TransactionMapper
) {
    @GetMapping
    fun findAll(): ResponseEntity<List<InvestmentDTO>> {
        log.debug("findAll")
        val investments: List<Investment> = investmentService.findAll()
        if (investments.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        val investmentDTOs: List<InvestmentDTO> = investments.map { investmentMapper.toInvestmentDTO(it) }
        return ResponseEntity.ok(investmentDTOs)
    }

    @GetMapping(path = ["/{depotId}"])
    fun findStocksInDepot(@PathVariable depotId: Long): ResponseEntity<List<InvestmentDTO>> {
        val investments: List<Investment> = investmentService.findByDepotId(depotId)
        if (investments.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(
            investments.map { investment: Investment ->
                val investmentDto = investmentMapper.toInvestmentDTO(investment)
                investmentDto.setYield(investment.calculateYield())
                investmentDto
            }
        )
    }

    @GetMapping(path = ["/transaction-history/{depotId}"])
    fun findTransactionsInDepot(@PathVariable depotId: Long): ResponseEntity<List<TransactionDTO>> {
        val investments: List<Investment> = investmentService.findByDepotId(depotId)
        if (investments.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        val transactions: List<TransactionDTO> =
            investments.map { transactionMapper.toTransactionDTO(it, it.investmentValue) }
        return ResponseEntity.ok(transactions)
    }

    @PostMapping
    fun create(@RequestBody investmentDTO: InvestmentDTO): ResponseEntity<Void> {
        log.debug("create: position={}", investmentDTO)
        val resultInvestment: Investment =
            investmentService.create(investmentMapper.toInvestment(investmentDTO)) ?: return ResponseEntity.badRequest()
                .build()
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(InvestmentResourceKotlin::class.java)
    }

}