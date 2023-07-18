package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    Optional<List<Investment>> findByDepotId(final long depotId);
}
