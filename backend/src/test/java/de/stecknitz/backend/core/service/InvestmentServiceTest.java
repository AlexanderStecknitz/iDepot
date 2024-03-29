package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.repository.InvestmentRepository;
import de.stecknitz.backend.core.service.exception.DepotNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class InvestmentServiceTest {

    @Mock
    InvestmentRepository investmentRepository;

    @Mock
    DepotService depotService;

    @Mock
    StockService stockService;

    @InjectMocks
    InvestmentService investmentService;

    @Test
    void findAllTest() {
        List<Investment> givenInvestments = List.of(
                Investment.builder()
                        .investmentId(TestUtil.INVESTMENT_ID_0)
                        .amount(TestUtil.AMOUNT)
                        .build(),
                Investment.builder()
                        .investmentId(TestUtil.INVESTMENT_ID_1)
                        .amount(TestUtil.AMOUNT)
                        .build()
        );

        Mockito.when(investmentRepository.findAll()).thenReturn(givenInvestments);

        List<Investment> foundInvestments = investmentService.findAll();

        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments);

    }

    @Test
    void findByDepotIdTest() {

        long givenDepotId = TestUtil.DEPOT_ID_0;

        List<Investment> givenInvestments = List.of(
                Investment.builder()
                        .depot(Depot.builder()
                                .id(givenDepotId)
                                .build())
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

        Optional<List<Investment>> givenInvestmentDTOs = Optional.of(List.of(givenInvestments.get(0)));

        Mockito.when(investmentRepository.findByDepotId(givenDepotId)).thenReturn(givenInvestmentDTOs);

        List<Investment> foundInvestments = investmentService.findByDepotId(givenDepotId);

        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments);

    }

    @Test
    void findByDepotIdWithNoInvestmentTest() {

        Mockito.when(investmentRepository.findByDepotId(0)).thenReturn(Optional.empty());

        List<Investment> result = investmentService.findByDepotId(0);

        Assertions.assertThat(result).isEmpty();

    }

    @Test
    void createTest() {

        long givenDepotId = TestUtil.DEPOT_ID_0;

        Depot givenDepot = Depot.builder()
                .id(givenDepotId)
                .build();

        Stock givenStock = Stock.builder()
                .isin(TestUtil.APPLE_ISIN)
                .build();

        Investment givenInvestments = Investment.builder()
                .investmentId(TestUtil.INVESTMENT_ID_0)
                .depot(givenDepot)
                .stock(givenStock)
                .build();

        Mockito.when(depotService.findById(givenDepotId)).thenReturn(givenDepot);
        Mockito.when(investmentRepository.saveAndFlush(givenInvestments)).thenReturn(givenInvestments);
        Mockito.when(stockService.findById(givenInvestments.getStock().getIsin())).thenReturn(givenStock);

        Investment foundInvestments = investmentService.create(givenInvestments);

        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments);

    }

    @Test
    void createTestWithNoDepot() {

        long givenDepotId = TestUtil.DEPOT_ID_0;

        Depot givenDepot = Depot.builder()
                .id(givenDepotId)
                .build();

        Stock givenOptionalStock = Stock.builder()
                .isin(TestUtil.APPLE_ISIN)
                .build();

        Investment givenInvestments = Investment.builder()
                .investmentId(TestUtil.INVESTMENT_ID_0)
                .depot(givenDepot)
                .stock(givenOptionalStock)
                .build();

        Mockito.when(depotService.findById(givenDepotId)).thenReturn(null);

        Assertions.assertThatThrownBy(() -> investmentService.create(givenInvestments))
                .isInstanceOf(DepotNotFoundException.class);

    }

    @Test
    void createTestWithNewStock() {

        long givenDepotId = TestUtil.DEPOT_ID_0;

        Depot givenDepot = Depot.builder()
                .id(givenDepotId)
                .build();

        Stock givenStock = Stock.builder()
                .isin(TestUtil.APPLE_ISIN)
                .wkn(TestUtil.APPLE_WKN)
                .name(TestUtil.APPLE_NAME)
                .currentPrice(TestUtil.APPLE_CURRENT_PRICE)
                .build();

        Investment givenInvestments = Investment.builder()
                .investmentId(TestUtil.INVESTMENT_ID_0)
                .depot(givenDepot)
                .stock(givenStock)
                .build();

        ArgumentCaptor<Stock> stockArgumentCaptor = ArgumentCaptor.forClass(Stock.class);

        Mockito.when(depotService.findById(givenDepotId)).thenReturn(givenDepot);
        Mockito.when(investmentRepository.saveAndFlush(givenInvestments)).thenReturn(givenInvestments);
        Mockito.when(stockService.findById(givenInvestments.getStock().getIsin())).thenReturn(null);

        Investment foundInvestments = investmentService.create(givenInvestments);

        Mockito.verify(stockService, Mockito.times(1)).create(stockArgumentCaptor.capture());

        Stock capturedStockArgument = stockArgumentCaptor.getValue();

        Assertions.assertThat(capturedStockArgument.getIsin()).isEqualTo(givenStock.getIsin());
        Assertions.assertThat(capturedStockArgument.getName()).isEqualTo(givenStock.getName());
        Assertions.assertThat(capturedStockArgument.getWkn()).isEqualTo(givenStock.getWkn());
        Assertions.assertThat(capturedStockArgument.getCurrentPrice()).isEqualTo(givenStock.getCurrentPrice());
        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments);

    }

    @Test
    void accumulateInvestmentValueTest() {
        final long depotId = TestUtil.DEPOT_ID_0;
        List<Investment> investments = List.of(
                Investment.builder()
                        .amount(TestUtil.AMOUNT)
                        .buyPrice(TestUtil.BUY_PRICE)
                        .build()
        );

        Optional<List<Investment>> optionalInvestments = Optional.of(
                investments
        );

        Mockito.when(investmentRepository.findByDepotId(depotId)).thenReturn(optionalInvestments);

        double accumulatedInvestmentValue = investmentService.accumulateInvestmentValue(depotId);

        Assertions.assertThat(accumulatedInvestmentValue).isEqualTo(investments.get(0).getInvestmentValue());

    }

}
