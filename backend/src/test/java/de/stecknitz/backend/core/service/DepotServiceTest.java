package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.DepositAccount;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.DepositAccountRepository;
import de.stecknitz.backend.core.repository.DepotRepository;
import de.stecknitz.backend.core.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DepotServiceTest {

    @Mock
    DepotRepository depotRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    DepositAccountRepository depositAccountRepository;

    @InjectMocks
    DepotService depotService;

    @Captor
    ArgumentCaptor<Depot> depotCaptor;

    @Captor
    ArgumentCaptor<DepositAccount> depositAccountCaptor;

    @Test
    void findAllDepotsTest() {

        List<Depot> givenDepots = List.of(
                Depot.builder()
                        .id(1)
                        .build(),
                Depot.builder()
                        .id(2)
                        .build());

        Mockito.when(depotRepository.findAll()).thenReturn(givenDepots);

        List<Depot> foundDepots = depotService.findAllDepots();

        Assertions.assertThat(foundDepots).isEqualTo(givenDepots);

    }

    @Test
    void findAllByEmailTest() {

        String email = "AlexanderStecknitz@Stecknitz.de";

        List<Depot> depots = List.of(
                Depot.builder()
                        .user(User.builder()
                                .email(email)
                                .build())
                        .build()
        );

        Mockito.when(depotRepository.findAllByUserEmail(email)).thenReturn(depots);

        List<Depot> foundDepots = depotService.findAllByEmail(email);

        Assertions.assertThat(foundDepots.get(0).getUser().getEmail()).isEqualTo(email);

    }

    @Test
    void createByUserTest() {

        String email = "AlexanderStecknitz@Stecknitz.de";

        User user = User.builder()
                .email(email)
                .build();

        depotService.createByUser(user);

        Mockito.verify(depotRepository).saveAndFlush(depotCaptor.capture());

        Assertions.assertThat(depotCaptor.getValue().getUser().getEmail()).isEqualTo(email);

    }

    @Test
    void createTest() {

        String email = "AlexanderStecknitz@Stecknitz.de";

        User user = User.builder()
                .email(email)
                .depots(Collections.emptyList())
                .build();

        Depot depot = Depot.builder()
                .user(user)
                .build();

        Depot depotWithId = Depot.builder()
                .id(1)
                .user(depot.getUser())
                .build();

        BDDMockito.given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        BDDMockito.given(depotRepository.saveAndFlush(depot)).willReturn(depotWithId);

        depotService.create(email);

        Mockito.verify(depotRepository).saveAndFlush(depotCaptor.capture());
        Mockito.verify(depositAccountRepository).saveAndFlush(depositAccountCaptor.capture());

        Assertions.assertThat(depotCaptor.getValue().getUser().getEmail()).isEqualTo(email);
        Assertions.assertThat(depositAccountCaptor.getValue().getDepot().getId()).isEqualTo(depotWithId.getId());
    }

}
