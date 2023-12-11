package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.service.ChartServiceKotlin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ChartResourceKotlin.class)
public class ChartResourceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ChartServiceKotlin chartService;

    @WithMockUser(username = "mock")
    @Test
    void getCompositionPieChartTest() throws Exception {
        final long depotId = TestUtil.DEPOT_ID_0;
        when(chartService.getCompositionPieChart(depotId)).thenReturn(Collections.emptyList());
        String ENDPOINT = "/api/chart/composition";
        mockMvc.perform(
                        get(ENDPOINT + "/" + depotId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

}
