package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.domain.Stock;
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
    public List<Investment> findAll() {
        List<Investment> investments = investmentRepository.findAll();
        return investments;
    }

    @Transactional(readOnly = true)
    public List<Investment> findByDepotId(final long depotId) {
        Optional<List<Investment>> optionalInvestments = investmentRepository.findByDepotId(depotId);
        return optionalInvestments.orElse(Collections.emptyList());
    }

    @Transactional
    public Investment create(final Investment investment) {
        log.debug("InvestmentService={}", investment);
        Optional<Depot> optionalDepot = depotRepository.findById(investment.getDepot().getId());
        if(optionalDepot.isEmpty()) {
            return null;
        }
        investment.setDepot(optionalDepot.get());
        Optional<Stock> optionalStock = stockRepository.findById(investment.getStock().getIsin());
        if(optionalStock.isEmpty()) {
            Stock stock = Stock.builder()
                    .isin(investment.getStock().getIsin())
                    .build();
            stockRepository.saveAndFlush(stock);
            investment.setStock(stock);
        } else {
            investment.setStock(optionalStock.get());
        }
        return investmentRepository.saveAndFlush(investment);
    }

}
