package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.BackendApplication;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.SharePosition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SharePositionRepositoryTest {

    @Autowired
    SharePositionRepository sharePositionRepository;

    @Autowired
    DepotRepository depotRepository;

    @Test
    void findByDepotIdTest() {
        sharePositionRepository.deleteAllInBatch();
        depotRepository.deleteAllInBatch();

        long givenDepotId = 1;

        Depot depot = Depot.builder()
                .id(givenDepotId)
                .build();

        depotRepository.saveAndFlush(depot);

        SharePosition sharePosition = SharePosition.builder()
                .sharePositionId(1)
                .depot(depot)
                .build();

        sharePositionRepository.saveAndFlush(sharePosition);

        Optional<List<SharePosition>> sharePositions = sharePositionRepository.findByDepotId(givenDepotId);

        Assertions.assertThat(sharePositions).isPresent();

        List<SharePosition> resultSharePosition = sharePositions.get();

        resultSharePosition.forEach(result -> Assertions.assertThat(result.getDepot().getId()).isEqualTo(givenDepotId));

    }

}
