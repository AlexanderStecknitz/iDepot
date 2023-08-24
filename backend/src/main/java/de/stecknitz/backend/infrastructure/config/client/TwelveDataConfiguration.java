package de.stecknitz.backend.infrastructure.config.client;

import de.stecknitz.backend.core.service.client.twelvedata.TwelveDataClient;
import de.stecknitz.backend.infrastructure.config.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class TwelveDataConfiguration {

    public static final String BEAN_TWELVE_DATA_WEB_CLIENT = "ALPHA_VANTAGE_WEB_CLIENT";

    private final ApplicationProperties applicationProperties;

    @Bean
    public TwelveDataClient twelveDataClient(@Qualifier(BEAN_TWELVE_DATA_WEB_CLIENT) final WebClient webClient) {
        return new TwelveDataClient(webClient, applicationProperties);
    }

    @Bean(BEAN_TWELVE_DATA_WEB_CLIENT)
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(applicationProperties.getTwelveData().getBaseUrl())
                .build();
    }

}
