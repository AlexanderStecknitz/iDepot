package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.User;
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
    public void createByUser(final User user) {
        Depot depot = Depot.builder()
                .user(user)
                .build();
        depotRepository.saveAndFlush(depot);
    }

    @Transactional
    public Depot create(String email) {
        Optional<Depot> optionalDepot = depotRepository.findByUserEmail(email);
        if(optionalDepot.isPresent()) {
            return null;
        }
        User user = User.builder()
                .email(email)
                .build();

        Depot depot = Depot.builder()
                .user(user)
                .build();

        return depotRepository.saveAndFlush(depot);
    }
}
