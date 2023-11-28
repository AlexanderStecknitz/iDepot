package de.stecknitz.backend.web.resources

import de.stecknitz.backend.core.domain.Depot
import de.stecknitz.backend.core.service.DepotService
import de.stecknitz.backend.web.resources.dto.DepotDTO
import de.stecknitz.backend.web.resources.dto.mapper.DepotMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/depot")
class DepotResourceKotlin(private val depotService: DepotService, private val depotMapper: DepotMapper) {

    @GetMapping
    fun findAll(): ResponseEntity<List<DepotDTO>> {
        val foundDepots: List<Depot> = depotService.findAllDepots();
        if (foundDepots.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        val foundDepotsDto: List<DepotDTO> = foundDepots.map { depotMapper.toDepotDTO(it) }
        return ResponseEntity.ok(foundDepotsDto)
    }

    @GetMapping(path = ["/{email}"])
    fun findAllByEmail(@PathVariable email: String): ResponseEntity<List<DepotDTO>> {
        val foundDepots: List<Depot> = depotService.findAllByEmail(email)
        if (foundDepots.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        val foundDepotsDto: List<DepotDTO> = foundDepots.map { depotMapper.toDepotDTO(it) }
        return ResponseEntity.ok(foundDepotsDto)
    }

    @PostMapping(path = ["/{email}"])
    fun create(@PathVariable email: String): ResponseEntity<Void> {
        depotService.create(email)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}