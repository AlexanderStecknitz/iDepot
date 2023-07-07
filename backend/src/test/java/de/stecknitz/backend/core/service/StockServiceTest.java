package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.repository.StockRepository;
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
class StockServiceTest {

    @Mock
    StockRepository stockRepository;

    @InjectMocks
    StockService stockService;

    @Test
    void findAllTest() {
        List<Stock> givenStocks = List.of(
                Stock.builder()
                        .isin("Test")
                        .name("Test")
                        .build(),
                Stock.builder()
                        .isin("Test2")
                        .name("Test2")
                        .build()
        );

        Mockito.when(stockRepository.findAll()).thenReturn(givenStocks);

        List<Stock> foundStocks = stockService.findAll();

        Assertions.assertThat(foundStocks).isEqualTo(givenStocks);

    }

    @Test
    void createTest() {
        Stock givenStock = Stock.builder()
                .isin("Test")
                .name("Test")
                .build();

        Optional<Stock> optionalShare = Optional.empty();

        Mockito.when(stockRepository.findById(givenStock.getIsin())).thenReturn(optionalShare);
        Mockito.when(stockRepository.saveAndFlush(givenStock)).thenReturn(givenStock);

        Stock foundStock = stockService.create(givenStock);

        Assertions.assertThat(foundStock).isEqualTo(givenStock);
    }

    @Test
    void createWithShareAlreadyExistsTest() {
        Stock givenStock = Stock.builder()
                .isin("Test")
                .name("Test")
                .build();

        Optional<Stock> optionalShare = Optional.of(
                Stock.builder()
                        .isin(givenStock.getIsin())
                        .name(givenStock.getName())
                        .build()
        );

        Mockito.when(stockRepository.findById(givenStock.getIsin())).thenReturn(optionalShare);

        Stock foundStock = stockService.create(givenStock);

        Assertions.assertThat(foundStock).isNull();
    }

}
