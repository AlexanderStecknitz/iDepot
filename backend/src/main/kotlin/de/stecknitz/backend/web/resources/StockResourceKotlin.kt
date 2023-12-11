package de.stecknitz.backend.web.resources

import de.stecknitz.backend.core.domain.StockKotlin
import de.stecknitz.backend.core.service.StockServiceKotlin
import de.stecknitz.backend.web.resources.dto.StockDTOKotlin
import de.stecknitz.backend.web.resources.dto.mapper.StockMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stock")
class StockResourceKotlin(private val stockService: StockServiceKotlin, private val stockMapper: StockMapper) {

    @GetMapping
    fun findAll(): ResponseEntity<List<StockDTOKotlin>> {
        log.debug("findAll")
        val foundStocks: List<StockKotlin> = stockService.findAll()
        if (foundStocks.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        val foundStockDTOs = foundStocks.map { stockMapper.toStockDto(it) }
        return ResponseEntity.ok(foundStockDTOs)
    }

    @PostMapping
    fun create(@RequestBody stockDTO: StockDTOKotlin): ResponseEntity<Void> {
        stockService.create(stockMapper.toStock(stockDTO)) ?: return ResponseEntity.badRequest().build()
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(StockResourceKotlin::class.java)
    }

}