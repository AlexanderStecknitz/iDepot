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
    void createTest() {

        Depot givenDepot = Depot.builder()
                .id(1)
                .build();

        Mockito.when(depotRepository.saveAndFlush(givenDepot)).thenReturn(givenDepot);

        Depot foundDepot = depotService.create(givenDepot);

        Assertions.assertThat(foundDepot).isEqualTo(givenDepot);
    }

}
