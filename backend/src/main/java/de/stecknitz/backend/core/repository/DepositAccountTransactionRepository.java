package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.DepositAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositAccountTransactionRepository extends JpaRepository<DepositAccountTransaction, Long> {
    Optional<List<DepositAccountTransaction>> findByDepositAccountId(final long depositAccountId);
}
