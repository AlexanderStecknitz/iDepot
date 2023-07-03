package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.core.service.ShareService;
import de.stecknitz.backend.web.resources.dto.ShareDTO;
import de.stecknitz.backend.web.resources.dto.mapper.ShareMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest( ShareResource.class )
class ShareResourceTest {

    private final static String ENDPOINT = "/api/share";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ShareService shareService;

    @MockBean
    ShareMapper shareMapper;

    @Test
    void findAllTest() throws Exception {

        List<Share> shares = List.of(
                Share.builder()
                        .isin("TEST1")
                .build()
        );

        ShareDTO shareDTO = ShareDTO.builder()
                .isin("TEST1")
                .build();

        given( shareService.findAll() ).willReturn(shares);
        given( shareMapper.toShareDto(shares.get(0)) ).willReturn(shareDTO);

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.[0].isin").value("TEST1"))
                .andReturn();

    }

    @Test
    void findAllButNoSharesTest() throws Exception {

        List<Share> shares = Collections.emptyList();

        given( shareService.findAll() ).willReturn(shares);

        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void createTest() throws Exception {
        ShareDTO shareDTO = ShareDTO.builder()
                .isin("TEST1")
                .build();

        Share share = Share.builder()
                .isin("TEST1")
                .build();

        given( shareMapper.toShare(shareDTO) ).willReturn(share);
        given( shareService.create(share) ).willReturn(share);

        mockMvc.perform(
                post(ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(shareDTO))
        )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void createButShareAlreadyExists() throws Exception {
        ShareDTO shareDTO = ShareDTO.builder()
                .isin("TEST1")
                .build();

        Share share = Share.builder()
                .isin("TEST1")
                .build();

        given( shareMapper.toShare(shareDTO) ).willReturn(share);
        given( shareService.create(share) ).willReturn(null);

        mockMvc.perform(
                        post(ENDPOINT)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(shareDTO))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

}
