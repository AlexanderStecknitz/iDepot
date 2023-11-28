package de.stecknitz.backend.web.resources

import de.stecknitz.backend.core.service.ChartService
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chart/composition")
class ChartResourceKotlin(private val chartService: ChartService) {

    @GetMapping(path = ["/{depotId}"])
    fun getCompositionPieChart(
        @PathVariable depotId: Long
    ): ResponseEntity<List<CompositionPieChartDTO>> {
        val compositionPieChartDTOs: List<CompositionPieChartDTO> = chartService.getCompositionPieChart(depotId)
        return ResponseEntity.ok(compositionPieChartDTOs)
    }
}