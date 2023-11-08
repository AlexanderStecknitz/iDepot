package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.DepositAccount;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.repository.DepositAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepositAccountService {

    private final DepositAccountRepository depositAccountRepository;

    @Transactional
    public void create(Depot depot) {
        DepositAccount depositAccount = DepositAccount.builder()
                .depot(depot)
                .build();
        depositAccountRepository.saveAndFlush(depositAccount);
    }

}
