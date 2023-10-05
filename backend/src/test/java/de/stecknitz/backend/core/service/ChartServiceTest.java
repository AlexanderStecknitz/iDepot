package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.repository.InvestmentRepository;
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTO;
import de.stecknitz.backend.web.resources.dto.mapper.CompositionPieChartMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ChartServiceTest {

    @Mock
    InvestmentRepository investmentRepository;

    @Mock
    CompositionPieChartMapper compositionPieChartMapper;

    @Mock
    InvestmentService investmentService;

    @InjectMocks
    ChartService chartService;

    @Test
    void getCompositionPieChartTest() {
        final long depotId = TestUtil.DEPOT_ID;
        Optional<List<Investment>> optionalInvestments = Optional.of(
                List.of(
                        Investment.builder()
                                .stock(TestUtil.STOCK_APPLE)
                                .buyPrice(TestUtil.BUY_PRICE)
                                .amount(TestUtil.AMOUNT)
                                .build()
                )
        );

        Investment investment = optionalInvestments.get().get(0);

        CompositionPieChartDTO compositionPieChartDTO = CompositionPieChartDTO.builder()
                .investmentName(TestUtil.APPLE_NAME)
                .investmentValue(investment.getInvestmentValue())
                .build();

        Mockito.when(investmentRepository.findByDepotId(depotId)).thenReturn(optionalInvestments);
        Mockito.when(investmentService.accumulateInvestmentValue(depotId)).thenReturn(investment.getInvestmentValue());
        Mockito.when(compositionPieChartMapper.toCompositionPieChartDTO(investment,
                investment.calculateInvestmentValue(investment.getInvestmentValue()))).thenReturn(compositionPieChartDTO);

        List<CompositionPieChartDTO> compositionPieChartDTOS = chartService.getCompositionPieChart(depotId);

        Assertions.assertThat(compositionPieChartDTOS.get(0)).isEqualTo(compositionPieChartDTO);
    }

    @Test
    void getCompositionPieChartNotFoundTest() {
        final long depotId = TestUtil.DEPOT_ID;

        Mockito.when(investmentRepository.findByDepotId(depotId)).thenReturn(Optional.empty());

        List<CompositionPieChartDTO> compositionPieChartDTOS = chartService.getCompositionPieChart(depotId);

        Assertions.assertThat(compositionPieChartDTOS).isEmpty();

    }

}
