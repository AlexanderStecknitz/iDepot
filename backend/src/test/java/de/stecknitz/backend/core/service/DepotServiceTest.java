package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.DepotRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class DepotServiceTest {

    @Mock
    DepotRepository depotRepository;

    @InjectMocks
    DepotService depotService;

    @Captor
    ArgumentCaptor<Depot> depotCaptor;

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

        depotService.create(email);

        Mockito.verify(depotRepository).saveAndFlush(depotCaptor.capture());

        Assertions.assertThat(depotCaptor.getValue().getUser().getEmail()).isEqualTo(email);
    }

}
