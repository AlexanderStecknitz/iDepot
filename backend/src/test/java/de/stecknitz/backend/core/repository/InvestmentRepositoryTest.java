package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Investment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
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
class InvestmentRepositoryTest {

    @Container
    static PostgreSQLContainer testContainer = new PostgreSQLContainer<>( "postgres:15.2" );

    @Autowired
    InvestmentRepository investmentRepository;

    @Autowired
    DepotRepository depotRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add( "spring.datasource.url", testContainer::getJdbcUrl );
        dynamicPropertyRegistry.add( "spring.datasource.username", testContainer::getUsername );
        dynamicPropertyRegistry.add( "spring.datasource.password", testContainer::getPassword );
    }

    @AfterAll
    static void afterAll() {
        testContainer.close();
    }

    @Test
    void findByDepotIdTest() {
        investmentRepository.deleteAllInBatch();
        depotRepository.deleteAllInBatch();

        long givenDepotId = 1;

        Depot depot = Depot.builder()
                .id(givenDepotId)
                .build();

        depotRepository.saveAndFlush(depot);

        Investment investment = Investment.builder()
                .investmentId(1)
                .depot(depot)
                .build();

        investmentRepository.saveAndFlush(investment);

        Optional<List<Investment>> investments = investmentRepository.findByDepotId(givenDepotId);

        Assertions.assertThat(investments).isPresent();

        List<Investment> resultInvestment = investments.get();

        resultInvestment.forEach(result -> Assertions.assertThat(result.getDepot().getId()).isEqualTo(givenDepotId));

    }

}
