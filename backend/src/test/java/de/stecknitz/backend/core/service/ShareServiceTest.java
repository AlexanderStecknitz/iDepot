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

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ShareServiceTest {

    @Mock
    private ShareRepository shareRepository;
    @InjectMocks
    private ShareService shareService;

    @Test
    void findAllTest() {
        final List<Share> givenShares = new ArrayList<>(List.of(
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

        Mockito.when(shareRepository.findAll()).thenReturn(givenShares);
        final List<Share> foundShares = shareService.findAll();

        Assertions.assertThat(givenShares).isEqualTo(foundShares);
    }

}
