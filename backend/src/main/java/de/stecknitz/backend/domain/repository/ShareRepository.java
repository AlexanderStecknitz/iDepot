package de.stecknitz.backend.domain.repository;

import de.stecknitz.backend.domain.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, String> {
    List<Share> findSharesByDepotsId(long depotId);
}
