package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTO;
import de.stecknitz.backend.web.resources.dto.mapper.CompositionPieChartMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ChartServiceTest {

    @Mock
    CompositionPieChartMapper compositionPieChartMapper;

    @Mock
    InvestmentService investmentService;

    @InjectMocks
    ChartService chartService;

    @Test
    void getCompositionPieChartTest() {
        final long depotId = TestUtil.DEPOT_ID_0;
        List<Investment> investments = List.of(
                Investment.builder()
                        .stock(Stock.builder()
                                .isin(TestUtil.APPLE_ISIN)
                                .wkn(TestUtil.APPLE_WKN)
                                .name(TestUtil.APPLE_NAME)
                                .currentPrice(TestUtil.APPLE_CURRENT_PRICE)
                                .symbol(TestUtil.APPLE_SYMBOL)
                                .build())
                        .buyPrice(TestUtil.BUY_PRICE)
                        .amount(TestUtil.AMOUNT)
                        .build()
        );

        Investment investment = investments.get(0);

        CompositionPieChartDTO compositionPieChartDTO = CompositionPieChartDTO.builder()
                .investmentName(TestUtil.APPLE_NAME)
                .investmentValue(investment.getInvestmentValue())
                .build();

        Mockito.when(investmentService.findByDepotId(depotId)).thenReturn(investments);
        Mockito.when(investmentService.accumulateInvestmentValue(depotId)).thenReturn(investment.getInvestmentValue());
        Mockito.when(compositionPieChartMapper.toCompositionPieChartDTO(investment,
                investment.calculateInvestmentValue(investment.getInvestmentValue()))).thenReturn(compositionPieChartDTO);

        List<CompositionPieChartDTO> compositionPieChartDTOS = chartService.getCompositionPieChart(depotId);

        Assertions.assertThat(compositionPieChartDTOS.get(0)).isEqualTo(compositionPieChartDTO);
    }

    @Test
    void getCompositionPieChartNotFoundTest() {
        final long depotId = TestUtil.DEPOT_ID_0;

        Mockito.when(investmentService.findByDepotId(depotId)).thenReturn(Collections.emptyList());

        List<CompositionPieChartDTO> compositionPieChartDTOS = chartService.getCompositionPieChart(depotId);

        Assertions.assertThat(compositionPieChartDTOS).isEmpty();

    }

}
