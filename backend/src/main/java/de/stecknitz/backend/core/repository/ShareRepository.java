package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, String> {
    List<Share> findSharesByDepotsId(long depotId);
}
