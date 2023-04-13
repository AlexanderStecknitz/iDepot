package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.core.repository.ShareRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ShareServiceTest {

    @Mock
    ShareRepository shareRepository;

    @InjectMocks
    ShareService shareService;

    @Test
    void findAllTest() {
        List<Share> givenShares = List.of(
                Share.builder()
                        .isin("Test")
                        .name("Test")
                        .build(),
                Share.builder()
                        .isin("Test2")
                        .name("Test2")
                        .build()
        );

        Mockito.when(shareRepository.findAll()).thenReturn(givenShares);

        List<Share> foundShares = shareService.findAll();

        Assertions.assertThat(foundShares).isEqualTo(givenShares);

    }

    @Test
    void createTest() {
        Share givenShare = Share.builder()
                .isin("Test")
                .name("Test")
                .build();

        Mockito.when(shareRepository.saveAndFlush(givenShare)).thenReturn(givenShare);

        Share foundShare = shareService.create(givenShare);

        Assertions.assertThat(foundShare).isEqualTo(givenShare);
    }

}
