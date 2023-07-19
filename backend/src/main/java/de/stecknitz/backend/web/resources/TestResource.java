package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.service.AlphaVantageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Slf4j
@RequiredArgsConstructor
public class TestResource {

    private final AlphaVantageService alphaVantageService;

    @GetMapping
    public String get() {
        log.debug("get");
        return alphaVantageService.get();
    }

}
