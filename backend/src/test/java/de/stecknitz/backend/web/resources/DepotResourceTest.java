package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.service.DepotService;
import de.stecknitz.backend.web.resources.dto.DepotDTO;
import de.stecknitz.backend.web.resources.dto.mapper.DepotMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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

@WebMvcTest(DepotResource.class)
class DepotResourceTest {

    private static final String ENDPOINT = "/api/depot";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepotService depotService;

    @MockBean
    private DepotMapper depotMapper;

    @WithMockUser(username = "mock")
    @Test
    void findAllTest() throws Exception {

        List<Depot> depots = List.of(
                Depot.builder()
                        .id(1)
                        .build()
        );

        List<DepotDTO> depotDTOS = List.of(
                DepotDTO.builder()
                        .id(1)
                        .build()
        );

        given(depotService.findAllDepots()).willReturn(depots);
        given(depotMapper.toDepotDTO(depots.get(0))).willReturn(depotDTOS.get(0));

        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();

    }

    @WithMockUser(username = "mock")
    @Test
    void findAllWithNoDepotsTest() throws Exception {

        List<Depot> depots = Collections.emptyList();

        given(depotService.findAllDepots()).willReturn(depots);

        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "mock")
    @Test
    void findAllByEmailTest() throws Exception {

        String email = "Test@Test.de";

        List<Depot> depots = List.of(
                Depot.builder()
                        .user(User.builder()
                                .email(email)
                                .build())
                        .build()
        );

        List<DepotDTO> depotDTOS = List.of(
                DepotDTO.builder()
                        .id(1)
                        .build()
        );

        given(depotService.findAllByEmail(email)).willReturn(depots);
        given(depotMapper.toDepotDTO(depots.get(0))).willReturn(depotDTOS.get(0));

        mockMvc.perform(get(ENDPOINT + "/" + email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();

    }

    @WithMockUser(username = "mock")
    @Test
    void findAllByEmailWithNoDepotsTest() throws Exception {

        String email = "Test@Test.de";

        List<Depot> depots = Collections.emptyList();

        given(depotService.findAllByEmail(email)).willReturn(depots);

        mockMvc.perform(get(ENDPOINT + "/" + email))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "mock")
    @Test
    void createTest() throws Exception {

        String email = "admin";

        mockMvc.perform(post(ENDPOINT + "/" + email).with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }

}
