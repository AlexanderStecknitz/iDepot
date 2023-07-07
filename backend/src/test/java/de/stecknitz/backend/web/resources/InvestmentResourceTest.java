package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.service.InvestmentService;
import de.stecknitz.backend.web.resources.dto.StockDTO;
import de.stecknitz.backend.web.resources.dto.InvestmentDTO;
import de.stecknitz.backend.web.resources.dto.mapper.InvestmentMapper;
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

@WebMvcTest( InvestmentResource.class )
class InvestmentResourceTest {

    private final String ENDPOINT = "/api/position/share";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    InvestmentService investmentService;

    @MockBean
    InvestmentMapper investmentMapper;

    @WithMockUser(username = "mock")
    @Test
    void findAllTest() throws Exception {
        List<Investment> sharePositions = List.of(
                Investment.builder()
                        .investmentId(1)
                        .stock(Stock.builder()
                                .isin("Test1")
                                .build())
                        .depot(Depot.builder()
                                .id(1)
                                .build())
                        .build()
        );

        InvestmentDTO investmentDTO = InvestmentDTO.builder()
                .stockDTO(StockDTO.builder()
                        .isin("ISIN1")
                        .build())
                .depotId(1)
                .amount(12)
                .buyPrice(54.21f)
                .build();

        given(investmentService.findAll()).willReturn(sharePositions);
        given(investmentMapper.toInvestmentDTO(sharePositions.get(0))).willReturn(investmentDTO);

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));

    }

    @WithMockUser(username = "mock")
    @Test
    void findAllButNoSharePositionsTest() throws Exception {
        List<Investment> sharePositions = Collections.emptyList();

        given(investmentService.findAll()).willReturn(sharePositions);

        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "mock")
    @Test
    void findSharesInDepotTest() throws Exception {
        final long depotId = 1;

        List<Investment> sharePositions = List.of(
                Investment.builder()
                        .depot(Depot.builder()
                                .id(1)
                                .build())
                        .investmentId(1)
                        .stock(Stock.builder()
                                .isin("Test")
                                .build())
                        .amount(12)
                        .buyPrice(98.21f)
                        .build()
        );

        InvestmentDTO investmentDTO = InvestmentDTO.builder()
                .stockDTO(StockDTO.builder()
                        .isin("Test")
                        .build())
                .depotId(1)
                .amount(12)
                .buyPrice(98.21f)
                .build();

        given(investmentService.findByDepotId(depotId)).willReturn(sharePositions);
        given(investmentMapper.toInvestmentDTO(sharePositions.get(0))).willReturn(investmentDTO);

        mockMvc.perform(get(ENDPOINT + "/" + depotId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));

    }

    @WithMockUser(username = "mock")
    @Test
    void findButNoSharesInDepotTest() throws Exception {
        final long depotId = 1;

        List<Investment> sharePositions = Collections.emptyList();

        given(investmentService.findByDepotId(depotId)).willReturn(sharePositions);

        mockMvc.perform(get(ENDPOINT + "/" + depotId))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @WithMockUser(username = "mock")
    @Test
    void createSharePositionTest() throws Exception {
        Investment sharePosition = Investment.builder()
                        .depot(Depot.builder()
                                .id(1)
                                .build())
                        .investmentId(1)
                        .stock(Stock.builder()
                                .isin("Test")
                                .build())
                        .amount(12)
                        .buyPrice(98.21f)
                        .build();

        InvestmentDTO investmentDTO = InvestmentDTO.builder()
                .stockDTO(StockDTO.builder()
                        .isin("Test")
                        .build())
                .depotId(1)
                .amount(12)
                .buyPrice(98.21f)
                .build();

        given(investmentMapper.toInvestment(investmentDTO)).willReturn(sharePosition);
        given(investmentService.create(sharePosition)).willReturn(sharePosition);

        mockMvc.perform(post(ENDPOINT).with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(investmentDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @WithMockUser(username = "mock")
    @Test
    void createButSharePositionAlreadyExistsTest() throws Exception {
        Investment sharePosition = Investment.builder()
                .depot(Depot.builder()
                        .id(1)
                        .build())
                .investmentId(1)
                .stock(Stock.builder()
                        .isin("Test")
                        .build())
                .amount(12)
                .buyPrice(98.21f)
                .build();

        InvestmentDTO investmentDTO = InvestmentDTO.builder()
                .stockDTO(StockDTO.builder()
                        .isin("Test")
                        .build())
                .depotId(1)
                .amount(12)
                .buyPrice(98.21f)
                .build();

        given(investmentMapper.toInvestment(investmentDTO)).willReturn(sharePosition);
        given(investmentService.create(sharePosition)).willReturn(null);

        mockMvc.perform(post(ENDPOINT).with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(investmentDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
