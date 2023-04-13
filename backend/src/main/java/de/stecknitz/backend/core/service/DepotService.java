package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.repository.DepotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepotService {

    private final DepotRepository depotRepository;

    @Transactional(readOnly = true)
    public List<Depot> findAllDepots() {
        List<Depot> foundDepots = depotRepository.findAll();
        log.debug("foundDepots={}", foundDepots);
        return foundDepots;
    }

    @Transactional
    public Depot create(final Depot depot) {
        Optional<Depot> optionalDepot = depotRepository.findById(depot.getId());
        if(optionalDepot.isPresent()) {
            return null;
        }
        return depotRepository.saveAndFlush(depot);
    }
}
