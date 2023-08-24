package de.stecknitz.backend.core.service.client.twelvedata;

import de.stecknitz.backend.core.service.client.twelvedata.dto.EndOfDayDTO;
import de.stecknitz.backend.infrastructure.config.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
public class TwelveDataClient {

    private final WebClient webClient;

    private final ApplicationProperties applicationProperties;

    public EndOfDayDTO getEndOfDay(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/eod")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", "1day")
                        .queryParam("apikey", applicationProperties.getTwelveData().getApiKey())
                        .build()
                )
                .retrieve()
                .bodyToMono(EndOfDayDTO.class)
                .block();
    }

}
