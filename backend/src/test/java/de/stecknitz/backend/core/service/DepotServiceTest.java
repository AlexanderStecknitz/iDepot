package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.User;
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
    DepositAccountService depositAccountService;

    @InjectMocks
    DepotService depotService;

    @Captor
    ArgumentCaptor<Depot> depotCaptor;

    @Test
    void findAllDepotsTest() {

        List<Depot> givenDepots = List.of(
                Depot.builder()
                        .id(TestUtil.DEPOT_ID_0)
                        .build(),
                Depot.builder()
                        .id(TestUtil.DEPOT_ID_1)
                        .build());

        Mockito.when(depotRepository.findAll()).thenReturn(givenDepots);

        List<Depot> foundDepots = depotService.findAllDepots();

        Assertions.assertThat(foundDepots).isEqualTo(givenDepots);

    }

    @Test
    void findAllByEmailTest() {

        String email = TestUtil.USER_EMAIL;

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
    void findByIdTest() {

        long id = TestUtil.DEPOT_ID_0;

        Depot depot = Depot.builder()
                .id(id)
                .build();

        Mockito.when(depotRepository.findById(id)).thenReturn(Optional.of(depot));

        Depot foundDepot = depotService.findById(id);

        Assertions.assertThat(foundDepot.getId()).isEqualTo(id);

    }

    @Test
    void findByIdNotFoundTest() {

        long id = TestUtil.DEPOT_ID_0;

        Mockito.when(depotRepository.findById(id)).thenReturn(Optional.empty());

        Depot foundDepot = depotService.findById(id);

        Assertions.assertThat(foundDepot).isNull();

    }

    @Test
    void createByUserTest() {

        String email = TestUtil.USER_EMAIL;

        User user = User.builder()
                .email(email)
                .build();

        depotService.createByUser(user);

        Mockito.verify(depotRepository).saveAndFlush(depotCaptor.capture());

        Assertions.assertThat(depotCaptor.getValue().getUser().getEmail()).isEqualTo(email);

    }

    @Test
    void createTest() {

        String email = "admin";

        Optional<User> user = Optional.of(User.builder()
                .email(email)
                .depots(Collections.emptyList())
                .build());

        Depot depot = Depot.builder()
                .user(user.get())
                .build();

        Depot depotWithId = Depot.builder()
                .id(1)
                .user(depot.getUser())
                .build();

        BDDMockito.given(userRepository.findByEmail(email)).willReturn(user);
        BDDMockito.given(depotRepository.saveAndFlush(depot)).willReturn(depotWithId);

        depotService.create(email);

        Mockito.verify(depositAccountService).create(depotCaptor.capture());

        Assertions.assertThat(depotCaptor.getValue().getUser().getEmail()).isEqualTo(email);
        Assertions.assertThat(depotCaptor.getValue().getId()).isEqualTo(depotWithId.getId());
    }

}
