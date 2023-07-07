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
        List<Investment> givenSharePositions = List.of(
                Investment.builder()
                        .investmentId(1)
                        .amount(1)
                        .build(),
                Investment.builder()
                        .investmentId(2)
                        .amount(2)
                        .build()
        );

        Mockito.when(investmentRepository.findAll()).thenReturn(givenSharePositions);

        List<Investment> foundSharePositions = investmentService.findAll();

        Assertions.assertThat(foundSharePositions).isEqualTo(givenSharePositions);

    }

    @Test
    void findByDepotIdTest() {

        long givenDepotId = 1;

        Optional<List<Investment>> givenSharePositions = Optional.of(List.of(
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

        Mockito.when(investmentRepository.findByDepotId(givenDepotId)).thenReturn(givenSharePositions);

        List<Investment> foundSharePositions = investmentService.findByDepotId(givenDepotId);

        Assertions.assertThat(foundSharePositions).isEqualTo(givenSharePositions.get());

    }

    @Test
    void createTest() {

        long givenDepotId = 1;

        Optional<Depot> givenDepot = Optional.of(Depot.builder()
                .id(givenDepotId)
                .build());

        Optional<Stock> givenShare = Optional.of(Stock.builder()
                .isin("ISIN1")
                .build());

        Investment givenSharePosition = Investment.builder()
                .investmentId(1)
                .depot(givenDepot.get())
                .stock(givenShare.get())
                .build();

        Mockito.when(depotRepository.findById(givenDepotId)).thenReturn(givenDepot);
        Mockito.when(investmentRepository.saveAndFlush(givenSharePosition)).thenReturn(givenSharePosition);
        Mockito.when(stockRepository.findById(givenSharePosition.getStock().getIsin())).thenReturn(givenShare);

        Investment foundSharePosition = investmentService.create(givenSharePosition);

        Assertions.assertThat(foundSharePosition).isEqualTo(givenSharePosition);

    }

    @Test
    void createTestWithNoDepot() {

        long givenDepotId = 1;

        Optional<Depot> givenOptionalDepot = Optional.empty();

        Optional<Depot> givenDepot = Optional.of(Depot.builder()
                .id(givenDepotId)
                .build());

        Optional<Stock> givenShare = Optional.of(Stock.builder()
                .isin("ISIN1")
                .build());

        Investment givenSharePosition = Investment.builder()
                .investmentId(1)
                .depot(givenDepot.get())
                .stock(givenShare.get())
                .build();

        Mockito.when(depotRepository.findById(givenDepotId)).thenReturn(givenOptionalDepot);

        Investment foundSharePosition = investmentService.create(givenSharePosition);

        Assertions.assertThat(foundSharePosition).isNull();

    }

    @Test
    void createTestWithNewStock() {

        long givenDepotId = 1;

        Optional<Depot> givenDepot = Optional.of(Depot.builder()
                .id(givenDepotId)
                .build());

        Optional<Stock> givenShare = Optional.of(Stock.builder()
                .isin("ISIN1")
                .wkn(null)
                .name(null)
                .actualPrice(0.0f)
                .build());

        Optional<Stock> givenOptionalShare = Optional.empty();

        Investment givenSharePosition = Investment.builder()
                .investmentId(1)
                .depot(givenDepot.get())
                .stock(givenShare.get())
                .build();

        ArgumentCaptor<Stock> shareArgumentCaptor = ArgumentCaptor.forClass(Stock.class);

        Mockito.when(depotRepository.findById(givenDepotId)).thenReturn(givenDepot);
        Mockito.when(investmentRepository.saveAndFlush(givenSharePosition)).thenReturn(givenSharePosition);
        Mockito.when(stockRepository.findById(givenSharePosition.getStock().getIsin())).thenReturn(givenOptionalShare);

        Investment foundSharePosition = investmentService.create(givenSharePosition);

        Mockito.verify(stockRepository, Mockito.times(1)).saveAndFlush(shareArgumentCaptor.capture());

        Stock capturedStockArgument = shareArgumentCaptor.getValue();

        Assertions.assertThat(capturedStockArgument).isEqualTo(givenShare.get());
        Assertions.assertThat(foundSharePosition).isEqualTo(givenSharePosition);

    }

}
