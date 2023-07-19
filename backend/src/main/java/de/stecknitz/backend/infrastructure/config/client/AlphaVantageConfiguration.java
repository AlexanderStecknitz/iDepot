package de.stecknitz.backend.infrastructure.config.client;

import de.stecknitz.backend.core.service.client.alphavantage.AlphaVantageClient;
import de.stecknitz.backend.infrastructure.config.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class AlphaVantageConfiguration {

    public static final String BEAN_ALPHA_VANTAGE_WEB_CLIENT = "ALPHA_VANTAGE_WEB_CLIENT";

    private final ApplicationProperties applicationProperties;

    @Bean
    public AlphaVantageClient alphaVantageClient(@Qualifier(BEAN_ALPHA_VANTAGE_WEB_CLIENT) final WebClient webClient) {
        return new AlphaVantageClient(webClient, applicationProperties);
    }

    @Bean(BEAN_ALPHA_VANTAGE_WEB_CLIENT)
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(applicationProperties.getAlphaVantage().getBaseUrl())
                .build();
    }

}
