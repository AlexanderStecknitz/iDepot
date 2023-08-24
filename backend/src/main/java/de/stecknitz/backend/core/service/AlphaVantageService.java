package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.repository.StockRepository;
import de.stecknitz.backend.core.service.client.alphavantage.AlphaVantageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlphaVantageService {

    private final AlphaVantageClient alphaVantageClient;
    private final StockRepository stockRepository;

    public Mono<String> get() {
        List<Stock> stocks = stockRepository.findAll();
        Mono<String> result = alphaVantageClient
                .getDailyAdjusted(stocks.get(0).getSymbol());
        return result;
    }

}
