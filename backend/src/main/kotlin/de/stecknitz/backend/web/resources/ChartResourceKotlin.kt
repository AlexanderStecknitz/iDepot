package de.stecknitz.backend.web.resources

import de.stecknitz.backend.core.service.ChartServiceKotlin
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTOKotlin
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chart/composition")
class ChartResourceKotlin(private val chartService: ChartServiceKotlin) {

    @GetMapping(path = ["/{depotId}"])
    fun getCompositionPieChart(
        @PathVariable depotId: Long
    ): ResponseEntity<List<CompositionPieChartDTOKotlin>> {
        val compositionPieChartDTOs: List<CompositionPieChartDTOKotlin> = chartService.getCompositionPieChart(depotId)
        return ResponseEntity.ok(compositionPieChartDTOs)
    }
}