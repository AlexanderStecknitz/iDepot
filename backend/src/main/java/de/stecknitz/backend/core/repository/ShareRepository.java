package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, String> {
}
