package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.core.repository.DepotRepository;
import de.stecknitz.backend.core.repository.ShareRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepotService {

    private final DepotRepository depotRepository;
    private final ShareRepository shareRepository;

    @Transactional
    public List<Depot> findAllDepots() {
        List<Depot> foundDepots = depotRepository.findAll();
        log.debug("foundDepots={}", foundDepots);
        return foundDepots;
    }

    @Transactional
    public List<Share> findAllSharesByDepotId(long depotId) {
        return shareRepository.findSharesByDepotsId(depotId);
    }

}
