package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.SharePosition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource( properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Testcontainers
class SharePositionRepositoryTest {

    @Container
    static PostgreSQLContainer testContainer = new PostgreSQLContainer<>( "postgres:15" );

    @Autowired
    SharePositionRepository sharePositionRepository;

    @Autowired
    DepotRepository depotRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add( "spring.datasource.url", testContainer::getJdbcUrl );
        dynamicPropertyRegistry.add( "spring.datasource.username", testContainer::getUsername );
        dynamicPropertyRegistry.add( "spring.datasource.password", testContainer::getPassword );
    }

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
