package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Stock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
public class StockRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    StockRepository stockRepository;

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
    void findByIsinTest() {
        stockRepository.deleteAllInBatch();

        Stock givenStock = Stock.builder()
                .isin(TestUtil.APPLE_ISIN)
                .wkn(TestUtil.APPLE_WKN)
                .name(TestUtil.APPLE_NAME)
                .symbol(TestUtil.APPLE_SYMBOL)
                .currentPrice(TestUtil.APPLE_CURRENT_PRICE)
                .build();

        stockRepository.saveAndFlush(givenStock);

        String isin = TestUtil.APPLE_ISIN;
        Stock stock = stockRepository.findByIsin(isin);

        Assertions.assertEquals(isin, stock.getIsin());
        Assertions.assertEquals(TestUtil.APPLE_NAME, stock.getName());
        Assertions.assertEquals(TestUtil.APPLE_SYMBOL, stock.getSymbol());
    }

}
