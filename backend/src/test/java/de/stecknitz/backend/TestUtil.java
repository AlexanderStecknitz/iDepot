package de.stecknitz.backend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.stecknitz.backend.core.domain.Stock;

public class TestUtil {

    public static final long DEPOT_ID = 0L;

    public static final long INVESTMENT_ID = 0L;

    public static final int AMOUNT = 10;

    public static final float BUY_PRICE = 110f;

    public static final Stock STOCK_APPLE = Stock.builder()
            .isin("US0378331005")
            .name("Apple Inc.")
            .symbol("AAPL")
            .currentPrice(110f)
            .wkn("865985")
            .build();

    public static final Stock STOCK_MICROSOFT = Stock.builder()
            .isin("US5949181045")
            .name("Microsoft")
            .wkn("870747")
            .symbol("MSFT")
            .currentPrice(301.5f)
            .build();

    public static byte[] convertObjectToJsonBytes(final Object object) throws  Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);

        return objectMapper.writeValueAsBytes(object);
    }

}
