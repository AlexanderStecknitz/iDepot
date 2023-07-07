package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Transactional
    public Stock create(final Stock stock) {
        Optional<Stock> optionalShare = stockRepository.findById(stock.getIsin());
        if(optionalShare.isPresent()) {
            return null;
        }
        return stockRepository.saveAndFlush(stock);
    }
}
