package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.domain.StockKotlin;
import de.stecknitz.backend.core.repository.InvestmentRepositoryKotlin;
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

@ExtendWith(MockitoExtension.class)
class InvestmentServiceTest {

    @Mock
    InvestmentRepositoryKotlin investmentRepository;

    @Mock
    DepotServiceKotlin depotService;

    @Mock
    StockServiceKotlin stockService;

    @InjectMocks
    InvestmentServiceKotlin investmentService;

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
                        .stock(new StockKotlin(
                                TestUtil.APPLE_ISIN,
                                TestUtil.APPLE_SYMBOL,
                                TestUtil.APPLE_WKN,
                                TestUtil.APPLE_NAME,
                                TestUtil.APPLE_CURRENT_PRICE))
                        .buyPrice(TestUtil.BUY_PRICE)
                        .amount(TestUtil.AMOUNT)
                        .build()
        );

        Mockito.when(investmentRepository.findByDepotId(givenDepotId)).thenReturn(givenInvestments);

        List<Investment> foundInvestments = investmentService.findByDepotId(givenDepotId);

        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments);

    }

    @Test
    void findByDepotIdWithNoInvestmentTest() {

        Mockito.when(investmentRepository.findByDepotId(0)).thenReturn(null);

        List<Investment> result = investmentService.findByDepotId(0);

        Assertions.assertThat(result).isEmpty();

    }

    @Test
    void createTest() {

        long givenDepotId = TestUtil.DEPOT_ID_0;

        Depot givenDepot = Depot.builder()
                .id(givenDepotId)
                .build();

        StockKotlin givenStock = new StockKotlin(TestUtil.APPLE_ISIN, "", "", "", 0f);

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

        StockKotlin givenOptionalStock = new StockKotlin(TestUtil.APPLE_ISIN, "", "", "", 0f);

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

        StockKotlin givenStock = new StockKotlin(TestUtil.APPLE_ISIN, "", TestUtil.APPLE_WKN, TestUtil.APPLE_NAME, TestUtil.APPLE_CURRENT_PRICE);

        Investment givenInvestments = Investment.builder()
                .investmentId(TestUtil.INVESTMENT_ID_0)
                .depot(givenDepot)
                .stock(givenStock)
                .build();

        ArgumentCaptor<StockKotlin> stockArgumentCaptor = ArgumentCaptor.forClass(StockKotlin.class);

        Mockito.when(depotService.findById(givenDepotId)).thenReturn(givenDepot);
        Mockito.when(investmentRepository.saveAndFlush(givenInvestments)).thenReturn(givenInvestments);
        Mockito.when(stockService.findById(givenInvestments.getStock().getIsin())).thenReturn(null);

        Investment foundInvestments = investmentService.create(givenInvestments);

        Mockito.verify(stockService, Mockito.times(1)).create(stockArgumentCaptor.capture());

        StockKotlin capturedStockArgument = stockArgumentCaptor.getValue();

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

        Mockito.when(investmentRepository.findByDepotId(depotId)).thenReturn(investments);

        double accumulatedInvestmentValue = investmentService.accumulateInvestmentValue(depotId);

        Assertions.assertThat(accumulatedInvestmentValue).isEqualTo(investments.get(0).getInvestmentValue());

    }

}
