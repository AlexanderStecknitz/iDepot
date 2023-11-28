package de.stecknitz.backend.web.resources

import de.stecknitz.backend.core.domain.Stock
import de.stecknitz.backend.core.service.StockService
import de.stecknitz.backend.web.resources.dto.StockDTO
import de.stecknitz.backend.web.resources.dto.mapper.StockMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stock")
class StockResourceKotlin(private val stockService: StockService, private val stockMapper: StockMapper) {

    @GetMapping
    fun findAll(): ResponseEntity<List<StockDTO>> {
        log.debug("findAll")
        val foundStocks: List<Stock> = stockService.findAll()
        if (foundStocks.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        val foundStockDTOs = foundStocks.map { stockMapper.toStockDto(it) }
        return ResponseEntity.ok(foundStockDTOs)
    }

    @PostMapping
    fun create(@RequestBody stockDTO: StockDTO): ResponseEntity<Void> {
        stockService.create(stockMapper.toStock(stockDTO)) ?: return ResponseEntity.badRequest().build()
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(StockResourceKotlin::class.java)
    }

}