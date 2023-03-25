package de.stecknitz.backend.domain.service;

import de.stecknitz.backend.domain.entity.Depot;
import de.stecknitz.backend.domain.repository.DepotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepotService {

    private final DepotRepository depotRepository;

    public List<Depot> findAll() {
        List<Depot> foundDepots = depotRepository.findAll();
        foundDepots.forEach(depot -> log.debug(depot.toString()) );
        return foundDepots;
    }

}
