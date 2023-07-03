package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.repository.DepotRepository;
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
class DepotServiceTest {

    @Mock
    DepotRepository depotRepository;

    @InjectMocks
    DepotService depotService;

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
    void createWithDepotAlreadyExitsTest() {

        Optional<Depot> optionalDepot = Optional.of(
                Depot.builder()
                        .id(1)
                        .build()
        );

        Depot givenDepot = Depot.builder()
                .id(1)
                .build();

        Mockito.when(depotRepository.findById(givenDepot.getId())).thenReturn(optionalDepot);

        Depot foundDepot = depotService.create(givenDepot);

        Assertions.assertThat(foundDepot).isNull();
    }

    @Test
    void createTest() {

        Depot givenDepot = Depot.builder()
                .id(1)
                .build();

        Optional<Depot> optionalDepot = Optional.empty();

        Mockito.when(depotRepository.saveAndFlush(givenDepot)).thenReturn(givenDepot);
        Mockito.when(depotRepository.findById(givenDepot.getId())).thenReturn(optionalDepot);

        Depot foundDepot = depotService.create(givenDepot);

        Assertions.assertThat(foundDepot).isEqualTo(givenDepot);
    }

}
