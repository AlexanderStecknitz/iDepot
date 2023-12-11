package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.domain.StockKotlin;
import de.stecknitz.backend.core.service.InvestmentServiceKotlin;
import de.stecknitz.backend.web.resources.dto.InvestmentDTOKotlin;
import de.stecknitz.backend.web.resources.dto.TransactionDTOKotlin;
import de.stecknitz.backend.web.resources.dto.mapper.InvestmentMapper;
import de.stecknitz.backend.web.resources.dto.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InvestmentResourceKotlin.class)
class InvestmentResourceTest {

    private final String ENDPOINT = "/api/investment";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    InvestmentServiceKotlin investmentService;

    @MockBean
    InvestmentMapper investmentMapper;

    @MockBean
    TransactionMapper transactionMapper;

    @WithMockUser(username = "mock")
    @Test
    void findAllTest() throws Exception {
        List<Investment> investments = List.of(
                Investment.builder()
                        .investmentId(TestUtil.INVESTMENT_ID_0)
                        .stock(new StockKotlin(
                                TestUtil.APPLE_ISIN,
                                "",
                                "",
                                "",
                                0f
                        ))
                        .depot(Depot.builder()
                                .id(TestUtil.DEPOT_ID_0)
                                .build())
                        .build()
        );

        InvestmentDTOKotlin investmentDTO = new InvestmentDTOKotlin(
                TestUtil.APPLE_ISIN,
                "",
                TestUtil.DEPOT_ID_0,
                TestUtil.AMOUNT,
                0f,
                TestUtil.BUY_PRICE,
                0f,
                ""
        );

        given(investmentService.findAll()).willReturn(investments);
        given(investmentMapper.toInvestmentDTO(investments.get(0))).willReturn(investmentDTO);

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));

    }

    @WithMockUser(username = "mock")
    @Test
    void findAllButNoInvestmentsTest() throws Exception {
        List<Investment> investments = Collections.emptyList();

        given(investmentService.findAll()).willReturn(investments);

        mockMvc.perform(get(ENDPOINT))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "mock")
    @Test
    void findStocksInDepotTest() throws Exception {
        final long depotId = TestUtil.DEPOT_ID_0;

        List<Investment> investments = List.of(
                Investment.builder()
                        .investmentId(TestUtil.INVESTMENT_ID_0)
                        .stock(new StockKotlin(TestUtil.APPLE_ISIN, TestUtil.APPLE_SYMBOL, TestUtil.APPLE_WKN, TestUtil.APPLE_NAME, TestUtil.APPLE_CURRENT_PRICE))
                        .depot(Depot.builder()
                                .id(depotId)
                                .build())
                        .amount(TestUtil.AMOUNT)
                        .buyPrice(TestUtil.BUY_PRICE)
                        .build()
        );

        InvestmentDTOKotlin investmentDTO = new InvestmentDTOKotlin(
                TestUtil.APPLE_ISIN,
                "",
                depotId,
                TestUtil.AMOUNT,
                0f,
                TestUtil.BUY_PRICE,
                investments.get(0).calculateYield(),
                ""
        );

        given(investmentService.findByDepotId(depotId)).willReturn(investments);
        given(investmentMapper.toInvestmentDTO(investments.get(0))).willReturn(investmentDTO);

        mockMvc.perform(get(ENDPOINT + "/" + depotId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));

    }

    @WithMockUser(username = "mock")
    @Test
    void findButNoStockInDepotTest() throws Exception {
        final long depotId = TestUtil.DEPOT_ID_0;

        List<Investment> investments = Collections.emptyList();

        given(investmentService.findByDepotId(depotId)).willReturn(investments);

        mockMvc.perform(get(ENDPOINT + "/" + depotId))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @WithMockUser(username = "mock")
    @Test
    void findTransactionsInDepotTest() throws Exception {
        final long depotId = TestUtil.DEPOT_ID_0;
        Depot depot = Depot.builder()
                .id(depotId)
                .build();
        StockKotlin stock = new StockKotlin(TestUtil.APPLE_ISIN, TestUtil.APPLE_SYMBOL, TestUtil.APPLE_WKN, TestUtil.APPLE_NAME, TestUtil.APPLE_CURRENT_PRICE);

        TransactionDTOKotlin transactionDTO = new TransactionDTOKotlin(Instant.now(), TestUtil.APPLE_NAME, TestUtil.BUY_PRICE, TestUtil.AMOUNT, 0.0, "");

        List<Investment> investments = List.of(
                Investment.builder()
                        .depot(depot)
                        .investmentId(TestUtil.INVESTMENT_ID_0)
                        .stock(stock)
                        .buyPrice(TestUtil.BUY_PRICE)
                        .amount(TestUtil.AMOUNT)
                        .build()
        );

        when(investmentService.findByDepotId(depotId)).thenReturn(investments);
        when(transactionMapper.toTransactionDTO(investments.get(0), investments.get(0).getInvestmentValue())).thenReturn(transactionDTO);

        mockMvc.perform(get(ENDPOINT + "/transaction-history/" + depotId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @WithMockUser(username = "mock")
    @Test
    void findTransactionsInDepotNotFoundTest() throws Exception {
        final long depotId = TestUtil.DEPOT_ID_0;

        when(investmentService.findByDepotId(depotId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get(ENDPOINT + "/transaction-history/" + depotId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "mock")
    @Test
    void createStockPositionTest() throws Exception {
        Investment investment = Investment.builder()
                .depot(Depot.builder()
                        .id(TestUtil.DEPOT_ID_0)
                        .build())
                .investmentId(TestUtil.INVESTMENT_ID_0)
                .stock(new StockKotlin(
                        TestUtil.APPLE_ISIN,
                        "",
                        "",
                        "",
                        0f
                ))
                .amount(TestUtil.AMOUNT)
                .buyPrice(TestUtil.BUY_PRICE)
                .build();

        InvestmentDTOKotlin investmentDTO = new InvestmentDTOKotlin(
                TestUtil.APPLE_ISIN,
                "",
                TestUtil.DEPOT_ID_0,
                TestUtil.AMOUNT,
                0f,
                TestUtil.BUY_PRICE,
                0f,
                ""
        );

        given(investmentMapper.toInvestment(investmentDTO)).willReturn(investment);
        given(investmentService.create(investment)).willReturn(investment);

        mockMvc.perform(post(ENDPOINT).with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(investmentDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @WithMockUser(username = "mock")
    @Test
    void createButStockPositionAlreadyExistsTest() throws Exception {
        Investment investment = Investment.builder()
                .depot(Depot.builder()
                        .id(TestUtil.DEPOT_ID_0)
                        .build())
                .investmentId(TestUtil.INVESTMENT_ID_0)
                .stock(new StockKotlin(
                        TestUtil.APPLE_ISIN,
                        "",
                        "",
                        "",
                        0f
                ))
                .amount(TestUtil.AMOUNT)
                .buyPrice(TestUtil.BUY_PRICE)
                .build();

        InvestmentDTOKotlin investmentDTO = new InvestmentDTOKotlin(
                TestUtil.APPLE_ISIN,
                "",
                TestUtil.DEPOT_ID_0,
                TestUtil.AMOUNT,
                0f,
                TestUtil.BUY_PRICE,
                0f,
                ""
        );

        given(investmentMapper.toInvestment(investmentDTO)).willReturn(investment);
        given(investmentService.create(investment)).willReturn(null);

        mockMvc.perform(post(ENDPOINT).with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(investmentDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
