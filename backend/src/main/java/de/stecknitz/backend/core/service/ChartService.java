package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.repository.InvestmentRepository;
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTO;
import de.stecknitz.backend.web.resources.dto.mapper.CompositionPieChartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final InvestmentRepository investmentRepository;
    private  final CompositionPieChartMapper compositionPieChartMapper;

    public List<CompositionPieChartDTO> getCompositionPieChart(long depotId) {
        Optional<List<Investment>>  optionalInvestments = investmentRepository.findByDepotId(depotId);
        List<Investment> investments = optionalInvestments.get();
        List<CompositionPieChartDTO> compositionPieChartDTOS = investments.stream()
                .map(compositionPieChartMapper::toCompositionPieChartDTO)
                .toList();
        return compositionPieChartDTOS;
    }

}
