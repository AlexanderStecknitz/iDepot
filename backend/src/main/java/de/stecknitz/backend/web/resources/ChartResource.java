package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.service.ChartService;
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/chart/composition")
@RequiredArgsConstructor
public class ChartResource {

    private final ChartService chartService;

    @GetMapping(path = "/{depotId}")
    public ResponseEntity<List<CompositionPieChartDTO>> getCompositionPieChart(
            @PathVariable long depotId
    ) {
        List<CompositionPieChartDTO> compositionPieChartDTOS = chartService.getCompositionPieChart(depotId);
        return ResponseEntity.ok(compositionPieChartDTOS);
    }

}
