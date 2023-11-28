package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.DepositAccount;
import de.stecknitz.backend.core.domain.DepositAccountTransaction;
import de.stecknitz.backend.core.service.DepositAccountTransactionService;
import de.stecknitz.backend.web.resources.dto.DepositAccountTransactionDTO;
import de.stecknitz.backend.web.resources.dto.mapper.DepositAccountTransactionMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.List;

@WebMvcTest(DepositAccountTransactionResourceKotlin.class)
class DepositAccountTransactionResourceTest {

    private final String ENDPOINT = "/api/deposit-account-transactions";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DepositAccountTransactionService depositAccountTransactionService;

    @MockBean
    DepositAccountTransactionMapper depositAccountTransactionMapper;

    @WithMockUser(username = "mock")
    @Test
    void getByDepositAccountIdTest() throws Exception {
        final long givenDepositAccountId = TestUtil.DEPOSIT_ACCOUNT_ID_0;

        DepositAccount givenDepositAccount = DepositAccount.builder()
                .id(givenDepositAccountId)
                .build();

        List<DepositAccountTransaction> givenDepositAccountTransactionList = List.of(
                DepositAccountTransaction.builder()
                        .id(TestUtil.DEPOSIT_ACCOUNT_TRANSACTION_ID_O)
                        .depositAccount(givenDepositAccount)
                        .amount(TestUtil.AMOUNT)
                        .type(TestUtil.DEPOSIT_ACCOUNT_TRANSACTION_TYPE)
                        .created_at(Timestamp.from(TestUtil.CURRENT_TIME))
                        .build()
        );

        DepositAccountTransactionDTO givenDepositAccountTransactionDTO = DepositAccountTransactionDTO.builder()
                .id(TestUtil.DEPOSIT_ACCOUNT_TRANSACTION_ID_O)
                .amount(TestUtil.AMOUNT)
                .created_at(TestUtil.CURRENT_TIME.toString())
                .type(TestUtil.DEPOSIT_ACCOUNT_TRANSACTION_TYPE)
                .build();

        Mockito.when(depositAccountTransactionService.findByDepositAccountId(givenDepositAccountId)).thenReturn(givenDepositAccountTransactionList);
        Mockito.when(depositAccountTransactionMapper.toDepositAccountTransactionDTO(givenDepositAccountTransactionList.get(0))).thenReturn(givenDepositAccountTransactionDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + givenDepositAccountId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is((int) TestUtil.DEPOSIT_ACCOUNT_TRANSACTION_ID_O)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount", Matchers.is(TestUtil.AMOUNT)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", Matchers.is(TestUtil.DEPOSIT_ACCOUNT_TRANSACTION_TYPE.toString())));
    }
}