package de.stecknitz.backend.core.repository;

import de.stecknitz.backend.BackendApplication;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Share;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShareRepositoryTest {

    /*
    @Autowired
    ShareRepository shareRepository;

    @Autowired
    DepotRepository depotRepository;

    @ParameterizedTest()
    @ValueSource(longs = {12})
    void findSharesByDepotsIdTest(long depotId) {
        final List<Depot> givenDepot = new ArrayList<>(List.of(
                Depot.builder()
                        .id(1)
                        .shares(null)
                        .build()
        ));

        final List<Share> givenShares = new ArrayList<>(List.of(
                Share.builder()
                        .isin("ISIN1")
                        .wkn("WKN1")
                        .name("Apple")
                        .amount(10)
                        .price(12.02f)
                        .depots(null)
                        .build(),
                Share.builder()
                        .isin("ISIN2")
                        .wkn("WKN2")
                        .name("Microsoft")
                        .amount(121)
                        .price(129.64f)
                        .depots(null)
                        .build()
        ));

        shareRepository.save(givenShares.get(0));
        depotRepository.save(givenDepot.get(0));

        Assertions.assertThat(shareRepository.findSharesByDepotsId(depotId)).isEqualTo(givenShares);
    }
     */

}
