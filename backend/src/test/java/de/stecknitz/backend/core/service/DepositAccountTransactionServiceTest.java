package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.DepositAccount;
import de.stecknitz.backend.core.domain.DepositAccountTransaction;
import de.stecknitz.backend.core.repository.DepositAccountTransactionRepositoryKotlin;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DepositAccountTransactionServiceTest {

    @Mock
    DepositAccountTransactionRepositoryKotlin depositAccountTransactionRepository;

    @InjectMocks
    DepositAccountTransactionServiceKotlin depositAccountTransactionService;

    @Test
    void findByDepositAccountIdTest() {
        final long givenDepositAccountId = TestUtil.DEPOT_ID_0;

        final DepositAccount givenDepositAccount = DepositAccount.builder()
                .id(givenDepositAccountId)
                .build();

        DepositAccountTransaction givenDepositAccountTransactions = DepositAccountTransaction.builder()
                .depositAccount(givenDepositAccount)
                .build();

        List<DepositAccountTransaction> givenDepositAccountTransactionList = List.of(givenDepositAccountTransactions);

        Mockito.when(depositAccountTransactionRepository.findByDepositAccountId(givenDepositAccountId)).thenReturn(Optional.of(givenDepositAccountTransactionList));

        List<DepositAccountTransaction> foundDepositAccountTransactionList = depositAccountTransactionService.findByDepositAccountId(givenDepositAccountId);

        Assertions.assertThat(foundDepositAccountTransactionList).isEqualTo(givenDepositAccountTransactionList);
    }
}