package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.repository.InvestmentRepository;
import de.stecknitz.backend.core.service.exception.DepotNotFoundException;
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
    private final DepotService depotService;
    private final StockService stockService;

    @Transactional(readOnly = true)
    public List<Investment> findAll() {
        return investmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Investment> findByDepotId(final long depotId) {
        Optional<List<Investment>> optionalInvestments = investmentRepository.findByDepotId(depotId);
        return optionalInvestments.orElse(Collections.emptyList());
    }

    @Transactional
    public Investment create(final Investment investment) {
        log.debug("InvestmentService={}", investment);
        Depot depot = depotService.findById(investment.getDepot().getId());
        if (depot == null) {
            throw new DepotNotFoundException();
        }
        investment.setDepot(depot);
        Stock stock = stockService.findById(investment.getStock().getIsin());
        if (stock == null) {
            stockService.create(investment.getStock());
            investment.setStock(investment.getStock());
        } else {
            investment.setStock(stock);
        }
        return investmentRepository.saveAndFlush(investment);
    }

    @Transactional(readOnly = true)
    public double accumulateInvestmentValue(final long depotId) {

        List<Investment> investments = this.findByDepotId(depotId);
        return investments.stream()
                .map(Investment::getInvestmentValue)
                .mapToDouble(Double::floatValue)
                .sum();

    }

}
