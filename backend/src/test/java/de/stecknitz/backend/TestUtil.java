package de.stecknitz.backend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestUtil {

    public static final String USER_EMAIL = "admin";

    public static final String USER_PASSWORD = "admin";

    public static final String USER_SALT = "salt";

    public static final String SECOND_USER_EMAIL = "user2";

    public static final String SECOND_USER_PASSWORD = "user2";

    public static final String SECOND_USER_FIRST_NAME = "user2";

    public static final String SECOND_USER_LAST_NAME = "user2";

    public static final long DEPOT_ID_0 = 0L;

    public static final long DEPOT_ID_1 = 1L;

    public static final long INVESTMENT_ID_0 = 0L;

    public static final long INVESTMENT_ID_1 = 1L;

    public static final int AMOUNT = 10;

    public static final float BUY_PRICE = 110f;

    public static final String APPLE_NAME = "Apple Inc.";

    public static final String APPLE_ISIN = "US0378331005";

    public static final String APPLE_SYMBOL = "AAPL";

    public static final String APPLE_WKN = "865985";

    public static final float APPLE_CURRENT_PRICE = 110f;

    public static final String MICROSOFT_NAME = "Microsoft";

    public static final String MICROSOFT_ISIN = "US5949181045";

    public static final String END_OF_DAY_CLOSE = "172.21";

    public static byte[] convertObjectToJsonBytes(final Object object) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);

        return objectMapper.writeValueAsBytes(object);
    }

}
