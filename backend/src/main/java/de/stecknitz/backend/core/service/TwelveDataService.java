package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.service.client.twelvedata.TwelveDataClient;
import de.stecknitz.backend.core.service.client.twelvedata.dto.EndOfDayDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwelveDataService {

    private final TwelveDataClient twelveDataClient;
    private final StockService stockService;

    @Transactional
    public EndOfDayDTO getEndOfDayData() {
        List<Stock> stocks = stockService.findAll();
        EndOfDayDTO endOfDayDTO = twelveDataClient
                .getEndOfDay(stocks.get(0).getSymbol());
        stocks.get(0).setCurrentPrice(Float.parseFloat(endOfDayDTO.getClose()));
        stockService.create(stocks.get(0));
        return endOfDayDTO;
    }

}
