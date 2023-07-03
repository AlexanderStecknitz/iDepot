package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.service.DepotService;
import de.stecknitz.backend.web.resources.dto.DepotDTO;
import de.stecknitz.backend.web.resources.dto.mapper.DepotMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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

@WebMvcTest( DepotResource.class )
@ExtendWith(MockitoExtension.class)
class DepotResourceTest {

    private static final String ENDPOINT = "/api/depot";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepotService depotService;

    @MockBean
    private DepotMapper depotMapper;

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

        given( depotService.findAllDepots() ).willReturn(depots);
        given( depotMapper.toDepotDTO(depots.get(0)) ).willReturn(depotDTOS.get(0));

        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();

    }

    @Test
    void findAllWithNoDepotsTest() throws Exception {

        List<Depot> depots = Collections.emptyList();

        given( depotService.findAllDepots() ).willReturn(depots);

        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createTest() throws Exception {

        final DepotDTO depotDTO = DepotDTO.builder()
                .id(1)
                .build();

        Depot depot = Depot.builder()
                .id(1)
                .build();

        given(depotMapper.toDepot(depotDTO)).willReturn(depot);
        given(depotService.create(depot)).willReturn(depot);

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(depotDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void createButDepotAlreadyExistsTest() throws Exception {

        final DepotDTO depotDTO = DepotDTO.builder()
                .id(1)
                .build();

        Depot depot = Depot.builder()
                .id(1)
                .build();

        given(depotMapper.toDepot(depotDTO)).willReturn(depot);
        given(depotService.create(depot)).willReturn(null);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(depotDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

}
