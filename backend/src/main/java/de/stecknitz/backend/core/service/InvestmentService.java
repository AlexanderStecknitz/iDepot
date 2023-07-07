package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.repository.DepotRepository;
import de.stecknitz.backend.core.repository.InvestmentRepository;
import de.stecknitz.backend.core.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final DepotRepository depotRepository;
    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public List<Investment> findAll() { return investmentRepository.findAll(); }

    @Transactional(readOnly = true)
    public List<Investment> findByDepotId(final long depotId) {
        Optional<List<Investment>> optionalDepot = investmentRepository.findByDepotId(depotId);
        return optionalDepot.orElse(Collections.emptyList());
    }

    @Transactional
    public Investment create(final Investment sharePosition) {
        log.debug("SharePositionService={}", sharePosition);
        Optional<Depot> optionalDepot = depotRepository.findById(sharePosition.getDepot().getId());
        if(optionalDepot.isEmpty()) {
            return null;
        }
        Optional<Stock> optionalShare = stockRepository.findById(sharePosition.getStock().getIsin());
        if(optionalShare.isEmpty()) {
            Stock stock = Stock.builder()
                    .isin(sharePosition.getStock().getIsin())
                    .build();
            stockRepository.saveAndFlush(stock);
        }
        return investmentRepository.saveAndFlush(sharePosition);
    }

}
