package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.core.repository.DepotRepository;
import de.stecknitz.backend.core.repository.ShareRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DepotServiceTest {

    @Mock
    DepotRepository depotRepository;
    @Mock
    ShareRepository shareRepository;

    @InjectMocks
    DepotService depotService;

    @Test
    void findAllDepotsTest() {
        List<Depot> givenDepots = new ArrayList<>(List.of(
                Depot.builder()
                        .id(1)
                        .shares(null)
                        .build(),
                Depot.builder()
                        .id(2)
                        .shares(null)
                        .build()
        ));

        Mockito.when(depotRepository.findAll()).thenReturn(givenDepots);
        List<Depot> foundDepots = depotService.findAllDepots();

        Assertions.assertThat(foundDepots).isEqualTo(givenDepots);
    }

    @Test
    void findAllSharesByDepotId() {
        List<Share> givenShares = new ArrayList<>(List.of(
                Share.builder()
                        .isin("ISIN1")
                        .wkn("WKN1")
                        .name("Apple")
                        .amount(10)
                        .price(12.02f)
                        .build(),
                Share.builder()
                        .isin("ISIN2")
                        .wkn("WKN2")
                        .name("Microsoft")
                        .amount(121)
                        .price(129.64f)
                        .build()
        ));

        long depotId = 1;

        Mockito.when(shareRepository.findSharesByDepotsId(depotId)).thenReturn(givenShares);
        List<Share> foundShares = depotService.findAllSharesByDepotId(depotId);

        Assertions.assertThat(foundShares).isEqualTo(givenShares);
    }

}
