package de.stecknitz.backend.core.service.client.alphavantage;

import de.stecknitz.backend.infrastructure.config.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class AlphaVantageClient {

    private final WebClient webClient;

    private final ApplicationProperties applicationProperties;

    public String test() {
        Mono<String> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/test")
                        .queryParam("apiKey", applicationProperties.getAlphaVantage().getApiKey())
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class);

        return result.subscribe(log::debug).toString();
    }

}
