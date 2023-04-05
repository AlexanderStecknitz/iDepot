package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.SharePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharePositionRepository extends JpaRepository<SharePosition, Long> {
    Optional<List<SharePosition>> findByDepotId(final long depotId);
}
