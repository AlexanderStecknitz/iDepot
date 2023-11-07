package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Investment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
class InvestmentRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    InvestmentRepository investmentRepository;

    @Autowired
    DepotRepository depotRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgres::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgres::getPassword);
    }

    @AfterAll
    static void afterAll() {
        postgres.close();
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @Test
    void findByDepotIdTest() {
        investmentRepository.deleteAllInBatch();
        depotRepository.deleteAllInBatch();

        long givenDepotId = TestUtil.DEPOT_ID_0;

        Depot depot = Depot.builder()
                .id(givenDepotId)
                .build();

        depotRepository.saveAndFlush(depot);

        Investment investment = Investment.builder()
                .investmentId(TestUtil.INVESTMENT_ID_0)
                .depot(depot)
                .build();

        investmentRepository.saveAndFlush(investment);

        Optional<List<Investment>> investments = investmentRepository.findByDepotId(givenDepotId);

        Assertions.assertThat(investments).isPresent();

        List<Investment> resultInvestment = investments.get();

        resultInvestment.forEach(result -> Assertions.assertThat(result.getDepot().getId()).isEqualTo(givenDepotId));

    }

}
