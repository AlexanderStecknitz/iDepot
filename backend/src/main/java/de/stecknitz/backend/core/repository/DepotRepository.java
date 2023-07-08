package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.Depot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepotRepository extends JpaRepository<Depot, Long> {
    Optional<Depot> findByUserEmail(final String email);
}
