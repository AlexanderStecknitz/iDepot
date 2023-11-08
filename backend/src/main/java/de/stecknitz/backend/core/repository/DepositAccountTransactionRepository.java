package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.DepositAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositAccountTransactionRepository extends JpaRepository<DepositAccountTransaction, Long> {
}
