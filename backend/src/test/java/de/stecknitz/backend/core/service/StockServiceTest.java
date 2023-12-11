package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.StockKotlin;
import de.stecknitz.backend.core.repository.StockRepositoryKotlin;
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
    StockRepositoryKotlin stockRepository;

    @InjectMocks
    StockServiceKotlin stockService;

    @Test
    void findAllTest() {
        List<StockKotlin> givenStocks = List.of(
                new StockKotlin(
                        TestUtil.APPLE_ISIN,
                        "",
                        "",
                        TestUtil.APPLE_NAME,
                        0f
                ),
                new StockKotlin(
                        TestUtil.MICROSOFT_ISIN,
                        "",
                        "",
                        TestUtil.MICROSOFT_NAME,
                        0f
                )
        );

        Mockito.when(stockRepository.findAll()).thenReturn(givenStocks);

        List<StockKotlin> foundStocks = stockService.findAll();

        Assertions.assertThat(foundStocks).isEqualTo(givenStocks);

    }

    @Test
    void findByIdTest() {
        StockKotlin givenStock = new StockKotlin(
                TestUtil.APPLE_ISIN,
                "",
                "",
                TestUtil.APPLE_NAME,
                0f
        );

        Optional<StockKotlin> optionalStock = Optional.of(givenStock);

        Mockito.when(stockRepository.findById(givenStock.getIsin())).thenReturn(optionalStock);

        StockKotlin foundStock = stockService.findById(givenStock.getIsin());

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
        StockKotlin givenStock = new StockKotlin(
                TestUtil.APPLE_ISIN,
                "",
                "",
                TestUtil.APPLE_NAME,
                0f
        );

        Optional<StockKotlin> optionalStock = Optional.empty();

        Mockito.when(stockRepository.findById(givenStock.getIsin())).thenReturn(optionalStock);
        Mockito.when(stockRepository.saveAndFlush(givenStock)).thenReturn(givenStock);

        StockKotlin foundStock = stockService.create(givenStock);

        Assertions.assertThat(foundStock).isEqualTo(givenStock);
    }

    @Test
    void createWithShareAlreadyExistsTest() {
        StockKotlin givenStock = new StockKotlin(
                TestUtil.APPLE_ISIN,
                "",
                "",
                TestUtil.APPLE_NAME,
                0f
        );

        Optional<StockKotlin> optionalStock = Optional.of(
                givenStock
        );

        Mockito.when(stockRepository.findById(givenStock.getIsin())).thenReturn(optionalStock);

        Assertions.assertThatThrownBy(() -> stockService.create(givenStock))
                .isInstanceOf(StockAlreadyExistsException.class);
    }

}
