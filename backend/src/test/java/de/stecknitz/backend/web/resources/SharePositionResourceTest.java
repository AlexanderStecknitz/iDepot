package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.core.domain.SharePosition;
import de.stecknitz.backend.core.service.SharePositionService;
import de.stecknitz.backend.web.resources.dto.ShareDTO;
import de.stecknitz.backend.web.resources.dto.SharePositionDTO;
import de.stecknitz.backend.web.resources.dto.mapper.SharePositionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest( SharePositionResource.class )
class SharePositionResourceTest {

    private static final String ENDPOINT = "/api/position/share";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SharePositionService sharePositionService;

    @MockBean
    SharePositionMapper sharePositionMapper;

    @Test
    void findAllTest() throws Exception {
        List<SharePosition> sharePositions = List.of(
                SharePosition.builder()
                        .sharePositionId(1)
                        .share(Share.builder()
                                .isin("Test1")
                                .build())
                        .depot(Depot.builder()
                                .id(1)
                                .build())
                        .build()
        );

        SharePositionDTO sharePositionDTO = SharePositionDTO.builder()
                .shareDTO(ShareDTO.builder()
                        .isin("ISIN1")
                        .build())
                .depotId(1)
                .amount(12)
                .buyPrice(54.21f)
                .build();

        given(sharePositionService.findAll()).willReturn(sharePositions);
        given(sharePositionMapper.toSharePositionDTO(sharePositions.get(0))).willReturn(sharePositionDTO);

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));

    }

    @Test
    void findAllButNoSharePositionsTest() throws Exception {
        List<SharePosition> sharePositions = Collections.emptyList();

        given(sharePositionService.findAll()).willReturn(sharePositions);

        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findSharesInDepotTest() throws Exception {
        final long depotId = 1;

        List<SharePosition> sharePositions = List.of(
                SharePosition.builder()
                        .depot(Depot.builder()
                                .id(1)
                                .build())
                        .sharePositionId(1)
                        .share(Share.builder()
                                .isin("Test")
                                .build())
                        .amount(12)
                        .buyPrice(98.21f)
                        .build()
        );

        SharePositionDTO sharePositionDTO = SharePositionDTO.builder()
                .shareDTO(ShareDTO.builder()
                        .isin("Test")
                        .build())
                .depotId(1)
                .amount(12)
                .buyPrice(98.21f)
                .build();

        given(sharePositionService.findByDepotId(depotId)).willReturn(sharePositions);
        given(sharePositionMapper.toSharePositionDTO(sharePositions.get(0))).willReturn(sharePositionDTO);

        mockMvc.perform(get(ENDPOINT + "/" + depotId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));

    }

    @Test
    void findButNoSharesInDepotTest() throws Exception {
        final long depotId = 1;

        List<SharePosition> sharePositions = Collections.emptyList();

        given(sharePositionService.findByDepotId(depotId)).willReturn(sharePositions);

        mockMvc.perform(get(ENDPOINT + "/" + depotId))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void createSharePositionTest() throws Exception {
        SharePosition sharePosition = SharePosition.builder()
                        .depot(Depot.builder()
                                .id(1)
                                .build())
                        .sharePositionId(1)
                        .share(Share.builder()
                                .isin("Test")
                                .build())
                        .amount(12)
                        .buyPrice(98.21f)
                        .build();

        SharePositionDTO sharePositionDTO = SharePositionDTO.builder()
                .shareDTO(ShareDTO.builder()
                        .isin("Test")
                        .build())
                .depotId(1)
                .amount(12)
                .buyPrice(98.21f)
                .build();

        given(sharePositionMapper.toSharePosition(sharePositionDTO)).willReturn(sharePosition);
        given(sharePositionService.create(sharePosition)).willReturn(sharePosition);

        mockMvc.perform(post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(sharePositionDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void createButSharePositionAlreadyExistsTest() throws Exception {
        SharePosition sharePosition = SharePosition.builder()
                .depot(Depot.builder()
                        .id(1)
                        .build())
                .sharePositionId(1)
                .share(Share.builder()
                        .isin("Test")
                        .build())
                .amount(12)
                .buyPrice(98.21f)
                .build();

        SharePositionDTO sharePositionDTO = SharePositionDTO.builder()
                .shareDTO(ShareDTO.builder()
                        .isin("Test")
                        .build())
                .depotId(1)
                .amount(12)
                .buyPrice(98.21f)
                .build();

        given(sharePositionMapper.toSharePosition(sharePositionDTO)).willReturn(sharePosition);
        given(sharePositionService.create(sharePosition)).willReturn(null);

        mockMvc.perform(post(ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(sharePositionDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
