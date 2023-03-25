package de.stecknitz.backend.domain.repository;

import de.stecknitz.backend.domain.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> { }
