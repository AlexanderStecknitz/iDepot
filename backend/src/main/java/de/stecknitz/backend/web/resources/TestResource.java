package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.service.TwelveDataService;
import de.stecknitz.backend.core.service.client.twelvedata.dto.EndOfDayDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Slf4j
@RequiredArgsConstructor
public class TestResource {

    private final TwelveDataService twelveDataService;

    @GetMapping
    public ResponseEntity<EndOfDayDTO> get() {
        log.debug("get");
        return ResponseEntity.ok(twelveDataService.get());
    }

}
