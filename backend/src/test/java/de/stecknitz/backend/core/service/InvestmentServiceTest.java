package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.repository.DepotRepository;
import de.stecknitz.backend.core.repository.InvestmentRepository;
import de.stecknitz.backend.core.repository.StockRepository;
import de.stecknitz.backend.web.resources.dto.InvestmentDTO;
import de.stecknitz.backend.web.resources.dto.mapper.InvestmentMapper;
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
    InvestmentMapper investmentMapper;

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

        Stock stock = Stock.builder()
                .isin("US0378331005")
                .wkn("865985")
                .name("Apple Inc.")
                .symbol("AAPL")
                .currentPrice(110f)
                .build();

        Optional<List<Investment>> givenInvestments = Optional.of(List.of(
                Investment.builder()
                        .depot(Depot.builder()
                                .id(givenDepotId)
                                .build())
                        .buyPrice(100f)
                        .amount(10f)
                        .stock(stock)
                        .build()
        ));

        List<InvestmentDTO> investmentDTOS = List.of(
                InvestmentDTO.builder()
                        .depotId(givenDepotId)
                        .buyPrice(100f)
                        .yield(0.1f)
                        .amount(10f)
                        .isin("US0378331005")
                        .build()
        );

        Mockito.when(investmentRepository.findByDepotId(givenDepotId)).thenReturn(givenInvestments);
        Mockito.when(stockRepository.findByIsin(investmentDTOS.get(0).getIsin())).thenReturn(stock);
        Mockito.when(investmentMapper.toInvestmentDTO(givenInvestments.get().get(0))).thenReturn(investmentDTOS.get(0));

        List<InvestmentDTO> foundInvestments = investmentService.findByDepotId(givenDepotId);

        Assertions.assertThat(foundInvestments).isEqualTo(investmentDTOS);

    }

    @Test
    void findByDepotIdWithNoInvestmentTest() {

        Mockito.when(investmentRepository.findByDepotId(0)).thenReturn(Optional.empty());

        List<InvestmentDTO> result = investmentService.findByDepotId(0);

        Assertions.assertThat(result).isEmpty();

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
                .currentPrice(0.0f)
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
        Assertions.assertThat(capturedStockArgument.getCurrentPrice()).isEqualTo(givenStock.get().getCurrentPrice());
        Assertions.assertThat(foundInvestments).isEqualTo(givenInvestments);

    }

}
