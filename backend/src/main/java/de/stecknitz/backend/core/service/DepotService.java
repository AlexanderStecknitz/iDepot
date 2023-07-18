package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.DepotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<Depot> findAllByEmail(final String email) {
        return depotRepository.findAllByUserEmail(email);
    }

    @Transactional
    public void createByUser(final User user) {
        Depot depot = Depot.builder()
                .user(user)
                .build();
        depotRepository.saveAndFlush(depot);
    }

    @Transactional
    public void create(String email) {
        User user = User.builder()
                .email(email)
                .build();

        Depot depot = Depot.builder()
                .user(user)
                .build();

        depotRepository.saveAndFlush(depot);
    }
}
