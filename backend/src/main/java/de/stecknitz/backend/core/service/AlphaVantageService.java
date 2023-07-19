package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.service.client.alphavantage.AlphaVantageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlphaVantageService {

    private final AlphaVantageClient alphaVantageClient;

    public String get() {
        return alphaVantageClient.test();
    }

}
