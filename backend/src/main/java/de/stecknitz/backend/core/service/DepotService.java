package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.DepositAccount;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.DepositAccountRepository;
import de.stecknitz.backend.core.repository.DepotRepository;
import de.stecknitz.backend.core.repository.UserRepository;
import de.stecknitz.backend.core.service.exception.UserNotFoundException;
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
    private final UserRepository userRepository;
    private final DepositAccountRepository depositAccountRepository;

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
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Depot depot = Depot.builder()
                .user(user.get())
                .build();

        Depot depotWithId = depotRepository.saveAndFlush(depot);

        DepositAccount depositAccount = DepositAccount.builder()
                .depot(depotWithId)
                .build();

        depositAccountRepository.saveAndFlush(depositAccount);
    }
}
