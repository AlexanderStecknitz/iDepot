package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.StockKotlin;
import de.stecknitz.backend.core.service.client.twelvedata.TwelveDataClientKotlin;
import de.stecknitz.backend.core.service.client.twelvedata.dto.EndOfDayDTOKotlin;
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
    TwelveDataClientKotlin twelveDataClient;

    @Mock
    StockServiceKotlin stockService;

    @InjectMocks
    TwelveDataServiceKotlin twelveDataService;

    @Captor
    ArgumentCaptor<StockKotlin> stockCaptor;

    @Test
    void getEndOfDayDataTest() {
        List<StockKotlin> stocks = List.of(
//                Stock.builder()
//                        .isin(TestUtil.APPLE_ISIN)
//                        .symbol(TestUtil.APPLE_SYMBOL)
//                        .build()
                new StockKotlin(TestUtil.APPLE_ISIN, TestUtil.APPLE_SYMBOL, "", "", 0f)
        );

        EndOfDayDTOKotlin endOfDayDTO = new EndOfDayDTOKotlin(TestUtil.APPLE_SYMBOL, "", "", "", "", TestUtil.END_OF_DAY_CLOSE);

        Mockito.when(stockService.findAll()).thenReturn(stocks);
        Mockito.when(twelveDataClient.getEndOfDay(stocks.get(0).getSymbol())).thenReturn(endOfDayDTO);

        EndOfDayDTOKotlin result = twelveDataService.getEndOfDayData();

        Mockito.verify(stockService, Mockito.times(1)).create(stockCaptor.capture());
        StockKotlin stock = stockCaptor.getValue();

        Assertions.assertEquals(stock.getCurrentPrice(), stocks.get(0).getCurrentPrice());
        Assertions.assertEquals(result, endOfDayDTO);

    }

}
