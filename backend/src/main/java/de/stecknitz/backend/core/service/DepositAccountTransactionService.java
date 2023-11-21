package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.DepositAccountTransaction;
import de.stecknitz.backend.core.repository.DepositAccountTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositAccountTransactionService {

    private final DepositAccountTransactionRepository depositAccountTransactionRepository;

    public List<DepositAccountTransaction> findByDepositAccountId(final long depositAccountId) {
        return depositAccountTransactionRepository.findByDepositAccountId(depositAccountId).orElse(Collections.emptyList());
    }

}
