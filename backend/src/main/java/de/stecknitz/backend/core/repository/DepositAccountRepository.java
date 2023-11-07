package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.DepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount, Integer> {
}
