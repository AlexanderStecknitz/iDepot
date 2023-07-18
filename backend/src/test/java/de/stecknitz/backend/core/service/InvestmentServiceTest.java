package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.repository.DepotRepository;
import de.stecknitz.backend.core.repository.InvestmentRepository;
import de.stecknitz.backend.core.repository.StockRepository;
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
    DepotRepository depotRepository;

    @Mock
    StockRepository stockRepository;

    @InjectMocks
    InvestmentService investmentService;

    @Test
    void findAllTest() {
        List<Investment> givenInvestments = List.of(
                Investment.builder()
                        .investmentId(1)
                        .amount(1)
                        .build(),
                Investment.builder()
                        .investmentId(2)
                        .amount(2)
                        .build()
        );

        Mockito.when(investmentRepository.findAll()).thenReturn(givenInvestments);

        List<Investment> foundInvestments = investmentService.findAll();

        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments);

    }

    @Test
    void findByDepotIdTest() {

        long givenDepotId = 1;

        Optional<List<Investment>> givenInvestments = Optional.of(List.of(
                Investment.builder()
                        .depot(Depot.builder()
                                .id(givenDepotId)
                                .build())
                        .build(),
                Investment.builder()
                        .depot(Depot.builder()
                                .id(givenDepotId)
                                .build())
                        .build()
        ));

        Mockito.when(investmentRepository.findByDepotId(givenDepotId)).thenReturn(givenInvestments);

        List<Investment> foundInvestments = investmentService.findByDepotId(givenDepotId);

        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments.get());

    }

    @Test
    void createTest() {

        long givenDepotId = 1;

        Optional<Depot> givenDepot = Optional.of(Depot.builder()
                .id(givenDepotId)
                .build());

        Optional<Stock> givenStock = Optional.of(Stock.builder()
                .isin("ISIN1")
                .build());

        Investment givenInvestments = Investment.builder()
                .investmentId(1)
                .depot(givenDepot.get())
                .stock(givenStock.get())
                .build();

        Mockito.when(depotRepository.findById(givenDepotId)).thenReturn(givenDepot);
        Mockito.when(investmentRepository.saveAndFlush(givenInvestments)).thenReturn(givenInvestments);
        Mockito.when(stockRepository.findById(givenInvestments.getStock().getIsin())).thenReturn(givenStock);

        Investment foundInvestments = investmentService.create(givenInvestments);

        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments);

    }

    @Test
    void createTestWithNoDepot() {

        long givenDepotId = 1;

        Optional<Depot> givenOptionalDepot = Optional.empty();

        Optional<Depot> givenDepot = Optional.of(Depot.builder()
                .id(givenDepotId)
                .build());

        Optional<Stock> givenOptionalStock = Optional.of(Stock.builder()
                .isin("ISIN1")
                .build());

        Investment givenInvestments = Investment.builder()
                .investmentId(1)
                .depot(givenDepot.get())
                .stock(givenOptionalStock.get())
                .build();

        Mockito.when(depotRepository.findById(givenDepotId)).thenReturn(givenOptionalDepot);

        Investment foundInvestments = investmentService.create(givenInvestments);

        Assertions.assertThat(foundInvestments).isNull();

    }

    @Test
    void createTestWithNewStock() {

        long givenDepotId = 1;

        Optional<Depot> givenDepot = Optional.of(Depot.builder()
                .id(givenDepotId)
                .build());

        Optional<Stock> givenStock = Optional.of(Stock.builder()
                .isin("ISIN1")
                .wkn(null)
                .name(null)
                .actualPrice(0.0f)
                .build());

        Optional<Stock> givenOptionalStock = Optional.empty();

        Investment givenInvestments = Investment.builder()
                .investmentId(1)
                .depot(givenDepot.get())
                .stock(givenStock.get())
                .build();

        ArgumentCaptor<Stock> stockArgumentCaptor = ArgumentCaptor.forClass(Stock.class);

        Mockito.when(depotRepository.findById(givenDepotId)).thenReturn(givenDepot);
        Mockito.when(investmentRepository.saveAndFlush(givenInvestments)).thenReturn(givenInvestments);
        Mockito.when(stockRepository.findById(givenInvestments.getStock().getIsin())).thenReturn(givenOptionalStock);

        Investment foundInvestments = investmentService.create(givenInvestments);

        Mockito.verify(stockRepository, Mockito.times(1)).saveAndFlush(stockArgumentCaptor.capture());

        Stock capturedStockArgument = stockArgumentCaptor.getValue();

        Assertions.assertThat(capturedStockArgument.getIsin()).isEqualTo(givenStock.get().getIsin());
        Assertions.assertThat(capturedStockArgument.getName()).isEqualTo(givenStock.get().getName());
        Assertions.assertThat(capturedStockArgument.getWkn()).isEqualTo(givenStock.get().getWkn());
        Assertions.assertThat(capturedStockArgument.getActualPrice()).isEqualTo(givenStock.get().getActualPrice());
        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments);

    }

}
