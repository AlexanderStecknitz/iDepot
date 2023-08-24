package de.stecknitz.backend.core.service.client.alphavantage;

import de.stecknitz.backend.infrastructure.config.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class AlphaVantageClient {

    private final WebClient webClient;

    private final ApplicationProperties applicationProperties;

    public Mono<String> getDailyAdjusted(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("function", "TIME_SERIES_DAILY")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", applicationProperties.getAlphaVantage().getApiKey())
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.debug("Received response: {}", response));
    }

}
