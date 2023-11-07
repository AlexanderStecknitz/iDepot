package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.repository.StockRepository;
import de.stecknitz.backend.core.service.client.twelvedata.TwelveDataClient;
import de.stecknitz.backend.core.service.client.twelvedata.dto.EndOfDayDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TwelveDataServiceTest {

    @Mock
    TwelveDataClient twelveDataClient;

    @Mock
    StockRepository stockRepository;

    @InjectMocks
    TwelveDataService twelveDataService;

    @Captor
    ArgumentCaptor<Stock> stockCaptor;

    @Test
    void getEndOfDayDataTest() {
        List<Stock> stocks = List.of(
                Stock.builder()
                        .isin(TestUtil.APPLE_ISIN)
                        .symbol(TestUtil.APPLE_SYMBOL)
                        .build()
        );

        EndOfDayDTO endOfDayDTO = EndOfDayDTO.builder()
                .symbol(TestUtil.APPLE_SYMBOL)
                .close(TestUtil.END_OF_DAY_CLOSE)
                .build();

        Mockito.when(stockRepository.findAll()).thenReturn(stocks);
        Mockito.when(twelveDataClient.getEndOfDay(stocks.get(0).getSymbol())).thenReturn(endOfDayDTO);

        EndOfDayDTO result = twelveDataService.getEndOfDayData();

        Mockito.verify(stockRepository, Mockito.times(1)).save(stockCaptor.capture());
        Stock stock = stockCaptor.getValue();

        Assertions.assertEquals(stock.getCurrentPrice(), stocks.get(0).getCurrentPrice());
        Assertions.assertEquals(result, endOfDayDTO);

    }

}
