package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.core.domain.Stock;
import org.junit.jupiter.api.Assertions;
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

@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource( properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@Testcontainers
public class StockRepositoryTest {

    @Container
    static PostgreSQLContainer testContainer = new PostgreSQLContainer<>( "postgres:15.2" );

    @Autowired
    StockRepository stockRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add( "spring.datasource.url", testContainer::getJdbcUrl );
        dynamicPropertyRegistry.add( "spring.datasource.username", testContainer::getUsername );
        dynamicPropertyRegistry.add( "spring.datasource.password", testContainer::getPassword );
    }

    @Test
    void findByIsinTest() {
        stockRepository.deleteAllInBatch();

        Stock givenStock = Stock.builder()
                .isin("US0378331005")
                .wkn("865985")
                .name("Apple Inc.")
                .symbol("AAPL")
                .currentPrice(0f)
                .build();

        stockRepository.saveAndFlush(givenStock);

        String isin = "US0378331005";
        Stock stock = stockRepository.findByIsin("US0378331005");

        Assertions.assertEquals(stock.getIsin(), isin);
        Assertions.assertEquals(stock.getName(), "Apple Inc.");
        Assertions.assertEquals(stock.getSymbol(), "AAPL");
    }

}
