package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.repository.StockRepository;
import de.stecknitz.backend.core.service.exception.StockAlreadyExistsException;
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
                        .isin(TestUtil.APPLE_ISIN)
                        .name(TestUtil.APPLE_NAME)
                        .build(),
                Stock.builder()
                        .isin(TestUtil.MICROSOFT_ISIN)
                        .name(TestUtil.MICROSOFT_NAME)
                        .build()
        );

        Mockito.when(stockRepository.findAll()).thenReturn(givenStocks);

        List<Stock> foundStocks = stockService.findAll();

        Assertions.assertThat(foundStocks).isEqualTo(givenStocks);

    }

    @Test
    void findByIdTest() {
        Stock givenStock = Stock.builder()
                .isin(TestUtil.APPLE_ISIN)
                .name(TestUtil.APPLE_NAME)
                .build();

        Optional<Stock> optionalStock = Optional.of(givenStock);

        Mockito.when(stockRepository.findById(givenStock.getIsin())).thenReturn(optionalStock);

        Stock foundStock = stockService.findById(givenStock.getIsin());

        Assertions.assertThat(foundStock).isEqualTo(givenStock);
    }

    @Test
    void findByIdWithStockNotFoundTest() {
        String isin = TestUtil.APPLE_ISIN;

        Mockito.when(stockRepository.findById(isin)).thenReturn(Optional.empty());

        Assertions.assertThat(stockService.findById(isin)).isNull();
    }

    @Test
    void createTest() {
        Stock givenStock = Stock.builder()
                .isin(TestUtil.APPLE_ISIN)
                .name(TestUtil.APPLE_NAME)
                .build();

        Optional<Stock> optionalStock = Optional.empty();

        Mockito.when(stockRepository.findById(givenStock.getIsin())).thenReturn(optionalStock);
        Mockito.when(stockRepository.saveAndFlush(givenStock)).thenReturn(givenStock);

        Stock foundStock = stockService.create(givenStock);

        Assertions.assertThat(foundStock).isEqualTo(givenStock);
    }

    @Test
    void createWithShareAlreadyExistsTest() {
        Stock givenStock = Stock.builder()
                .isin(TestUtil.APPLE_ISIN)
                .name(TestUtil.APPLE_NAME)
                .build();

        Optional<Stock> optionalStock = Optional.of(
                Stock.builder()
                        .isin(givenStock.getIsin())
                        .name(givenStock.getName())
                        .build()
        );

        Mockito.when(stockRepository.findById(givenStock.getIsin())).thenReturn(optionalStock);

        Assertions.assertThatThrownBy(() -> stockService.create(givenStock))
                .isInstanceOf(StockAlreadyExistsException.class);
    }

}
