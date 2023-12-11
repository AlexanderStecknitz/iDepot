package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.DepositAccount;
import de.stecknitz.backend.core.domain.DepositAccountTransaction;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
public class DepositAccountTransactionRepositoryTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    DepositAccountTransactionRepositoryKotlin depositAccountTransactionRepository;

    @Autowired
    DepositAccountRepositoryKotlin depositAccountRepository;

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
        depositAccountTransactionRepository.deleteAllInBatch();
        final long depositAccountId = 1L;

        final Instant createdAt = TestUtil.CURRENT_TIME;

        DepositAccount givenDepositAccount = DepositAccount.builder()
                .id(depositAccountId)
                .build();

        depositAccountRepository.saveAndFlush(givenDepositAccount);

        DepositAccountTransaction givenDepositAccountTransaction = DepositAccountTransaction.builder()
                .amount(TestUtil.AMOUNT)
                .type(TestUtil.DEPOSIT_ACCOUNT_TRANSACTION_TYPE)
                .created_at(Timestamp.from(createdAt))
                .depositAccount(givenDepositAccount)
                .build();

        depositAccountTransactionRepository.saveAndFlush(givenDepositAccountTransaction);

        Optional<List<DepositAccountTransaction>> foundDepositAccountTransactionList = depositAccountTransactionRepository.findByDepositAccountId(depositAccountId);

        Assertions.assertThat(foundDepositAccountTransactionList).isPresent();

        DepositAccountTransaction foundDepositAccountTransaction = foundDepositAccountTransactionList.get().get(0);

        Assertions.assertThat(foundDepositAccountTransaction.getAmount()).isEqualTo(TestUtil.AMOUNT);
        Assertions.assertThat(foundDepositAccountTransaction.getType()).isEqualTo(TestUtil.DEPOSIT_ACCOUNT_TRANSACTION_TYPE);
        Assertions.assertThat(foundDepositAccountTransaction.getCreated_at()).isEqualTo(Timestamp.from(createdAt));

    }

}
