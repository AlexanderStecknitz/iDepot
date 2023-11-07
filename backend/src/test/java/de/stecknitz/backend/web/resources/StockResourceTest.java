package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.service.StockService;
import de.stecknitz.backend.web.resources.dto.StockDTO;
import de.stecknitz.backend.web.resources.dto.mapper.StockMapper;
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

@WebMvcTest(StockResource.class)
class StockResourceTest {

    private final static String ENDPOINT = "/api/stock";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StockService stockService;

    @MockBean
    StockMapper stockMapper;

    @WithMockUser(username = "mock")
    @Test
    void findAllTest() throws Exception {

        List<Stock> stocks = List.of(
                Stock.builder()
                        .isin(TestUtil.APPLE_ISIN)
                        .build()
        );

        StockDTO stockDTO = StockDTO.builder()
                .isin(TestUtil.APPLE_ISIN)
                .build();

        given(stockService.findAll()).willReturn(stocks);
        given(stockMapper.toStockDto(stocks.get(0))).willReturn(stockDTO);

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.[0].isin").value(TestUtil.APPLE_ISIN))
                .andReturn();

    }

    @WithMockUser(username = "mock")
    @Test
    void findAllButNoSharesTest() throws Exception {

        List<Stock> stocks = Collections.emptyList();

        given(stockService.findAll()).willReturn(stocks);

        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @WithMockUser(username = "mock")
    @Test
    void createTest() throws Exception {
        StockDTO stockDTO = StockDTO.builder()
                .isin(TestUtil.APPLE_ISIN)
                .build();

        Stock stock = Stock.builder()
                .isin(TestUtil.APPLE_ISIN)
                .build();

        given(stockMapper.toStock(stockDTO)).willReturn(stock);
        given(stockService.create(stock)).willReturn(stock);

        mockMvc.perform(
                        post(ENDPOINT).with(SecurityMockMvcRequestPostProcessors.csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(stockDTO))
                )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @WithMockUser(username = "mock")
    @Test
    void createButShareAlreadyExists() throws Exception {
        StockDTO stockDTO = StockDTO.builder()
                .isin(TestUtil.APPLE_ISIN)
                .build();

        Stock stock = Stock.builder()
                .isin(TestUtil.APPLE_ISIN)
                .build();

        given(stockMapper.toStock(stockDTO)).willReturn(stock);
        given(stockService.create(stock)).willReturn(null);

        mockMvc.perform(
                        post(ENDPOINT).with(SecurityMockMvcRequestPostProcessors.csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(stockDTO))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

}
