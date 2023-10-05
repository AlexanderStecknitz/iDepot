package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.repository.InvestmentRepository;
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTO;
import de.stecknitz.backend.web.resources.dto.mapper.CompositionPieChartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final InvestmentRepository investmentRepository;
    private final CompositionPieChartMapper compositionPieChartMapper;
    private final InvestmentService investmentService;

    public List<CompositionPieChartDTO> getCompositionPieChart(long depotId) {
        Optional<List<Investment>> optionalInvestments = investmentRepository.findByDepotId(depotId);
        if (optionalInvestments.isEmpty()) {
            return Collections.emptyList();
        }
        List<Investment> investments = optionalInvestments.get();

        double accumulatedInvestmentValue = investmentService.accumulateInvestmentValue(depotId);

        List<CompositionPieChartDTO> compositionPieChartDTOS = investments.stream()
                .map(investment -> {

                    CompositionPieChartDTO compositionPieChartDTO = compositionPieChartMapper
                            .toCompositionPieChartDTO(
                                    investment,
                                    investment.calculateInvestmentValue(accumulatedInvestmentValue));

                    return compositionPieChartDTO;

                })
                .toList();

        return compositionPieChartDTOS;
    }

}
