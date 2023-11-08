package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.DepositAccount;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.repository.DepositAccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DepositAccountServiceTest {

    @Mock
    DepositAccountRepository depositAccountRepository;

    @InjectMocks
    DepositAccountService depositAccountService;

    @Captor
    ArgumentCaptor<DepositAccount> depositAccountArgumentCaptor;

    @Test
    void createTest() {
        Depot depot = Depot.builder()
                .user(null)
                .build();

        depositAccountService.create(depot);

        Mockito.verify(depositAccountRepository, Mockito.times(1)).saveAndFlush(depositAccountArgumentCaptor.capture());

        DepositAccount depositAccount = depositAccountArgumentCaptor.getValue();

        Assertions.assertThat(depositAccount.getDepot()).isEqualTo(depot);
    }

}
