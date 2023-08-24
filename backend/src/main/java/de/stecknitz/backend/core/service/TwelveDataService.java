package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.repository.StockRepository;
import de.stecknitz.backend.core.service.client.twelvedata.TwelveDataClient;
import de.stecknitz.backend.core.service.client.twelvedata.dto.EndOfDayDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwelveDataService {

    private final TwelveDataClient twelveDataClient;
    private final StockRepository stockRepository;

    public EndOfDayDTO get() {
        List<Stock> stocks = stockRepository.findAll();
        EndOfDayDTO endOfDayDTO = twelveDataClient
                .getEndOfDay(stocks.get(0).getSymbol());
        stocks.get(0).setActualPrice(Float.parseFloat(endOfDayDTO.getClose()));
        stockRepository.save(stocks.get(0));
        return endOfDayDTO;
    }

}
