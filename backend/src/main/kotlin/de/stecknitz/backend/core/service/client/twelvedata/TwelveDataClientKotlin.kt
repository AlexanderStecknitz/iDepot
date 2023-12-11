package de.stecknitz.backend.core.service.client.twelvedata

import de.stecknitz.backend.core.service.client.twelvedata.dto.EndOfDayDTOKotlin
import de.stecknitz.backend.infrastructure.config.properties.ApplicationProperties
import org.springframework.web.reactive.function.client.WebClient

class TwelveDataClientKotlin(
    private val webClient: WebClient,
    private val applicationProperties: ApplicationProperties
) {

    fun getEndOfDay(symbol: String): EndOfDayDTOKotlin? = webClient.get()
        .uri {
            it.path("/eod")
                .queryParam("symbol", symbol)
                .queryParam("interval", "1day")
                .queryParam("apikey", applicationProperties.twelveData.apiKey).build()
        }
        .retrieve()
        .bodyToMono(EndOfDayDTOKotlin::class.java)
        .block()
}