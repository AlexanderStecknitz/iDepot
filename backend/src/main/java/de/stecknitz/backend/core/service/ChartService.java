package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTO;
import de.stecknitz.backend.web.resources.dto.mapper.CompositionPieChartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final CompositionPieChartMapper compositionPieChartMapper;
    private final InvestmentService investmentService;

    @Transactional(readOnly = true)
    public List<CompositionPieChartDTO> getCompositionPieChart(long depotId) {
        List<Investment> investments = investmentService.findByDepotId(depotId);
        if (investments.isEmpty()) {
            return Collections.emptyList();
        }
        double accumulatedInvestmentValue = investmentService.accumulateInvestmentValue(depotId);

        return investments.stream()
                .map(investment -> compositionPieChartMapper
                        .toCompositionPieChartDTO(
                                investment,
                                investment.calculateInvestmentValue(accumulatedInvestmentValue)))
                .toList();
    }

}
