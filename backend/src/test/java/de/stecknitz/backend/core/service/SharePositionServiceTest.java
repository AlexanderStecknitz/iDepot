package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.core.domain.SharePosition;
import de.stecknitz.backend.core.repository.DepotRepository;
import de.stecknitz.backend.core.repository.SharePositionRepository;
import de.stecknitz.backend.core.repository.ShareRepository;
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
class SharePositionServiceTest {

    @Mock
    SharePositionRepository sharePositionRepository;

    @Mock
    DepotRepository depotRepository;

    @Mock
    ShareRepository shareRepository;

    @InjectMocks
    SharePositionService sharePositionService;

    @Test
    void findAllTest() {
        List<SharePosition> givenSharePositions = List.of(
                SharePosition.builder()
                        .sharePositionId(1)
                        .amount(1)
                        .build(),
                SharePosition.builder()
                        .sharePositionId(2)
                        .amount(2)
                        .build()
        );

        Mockito.when(sharePositionRepository.findAll()).thenReturn(givenSharePositions);

        List<SharePosition> foundSharePositions = sharePositionService.findAll();

        Assertions.assertThat(foundSharePositions).isEqualTo(givenSharePositions);

    }

    @Test
    void findByDepotIdTest() {

        long givenDepotId = 1;

        Optional<List<SharePosition>> givenSharePositions = Optional.of(List.of(
                SharePosition.builder()
                        .depot(Depot.builder()
                                .id(givenDepotId)
                                .build())
                        .build(),
                SharePosition.builder()
                        .depot(Depot.builder()
                                .id(givenDepotId)
                                .build())
                        .build()
        ));

        Mockito.when(sharePositionRepository.findByDepotId(givenDepotId)).thenReturn(givenSharePositions);

        List<SharePosition> foundSharePositions = sharePositionService.findByDepotId(givenDepotId);

        Assertions.assertThat(foundSharePositions).isEqualTo(givenSharePositions.get());

    }

    @Test
    void createTest() {

        long givenDepotId = 1;

        Optional<Depot> givenDepot = Optional.of(Depot.builder()
                .id(givenDepotId)
                .build());

        Optional<Share> givenShare = Optional.of(Share.builder()
                .isin("ISIN1")
                .build());

        SharePosition givenSharePosition = SharePosition.builder()
                .sharePositionId(1)
                .depot(givenDepot.get())
                .share(givenShare.get())
                .build();

        Mockito.when(depotRepository.findById(givenDepotId)).thenReturn(givenDepot);
        Mockito.when(sharePositionRepository.saveAndFlush(givenSharePosition)).thenReturn(givenSharePosition);
        Mockito.when(shareRepository.findById(givenSharePosition.getShare().getIsin())).thenReturn(givenShare);

        SharePosition foundSharePosition = sharePositionService.create(givenSharePosition);

        Assertions.assertThat(foundSharePosition).isEqualTo(givenSharePosition);

    }

}
