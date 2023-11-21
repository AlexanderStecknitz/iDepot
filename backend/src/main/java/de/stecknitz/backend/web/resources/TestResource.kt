package de.stecknitz.backend.web.resources

import de.stecknitz.backend.core.service.TwelveDataService
import de.stecknitz.backend.core.service.client.twelvedata.dto.EndOfDayDTO
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
class TestResource(private val twelveDataService: TwelveDataService) {

    @GetMapping
    fun get(): ResponseEntity<EndOfDayDTO> {
        log.debug("get")
        return ResponseEntity.ok(twelveDataService.getEndOfDayData())
    }

    @GetMapping(path = ["/test"])
    fun getTest() {
        log.debug("getTest")
    }

    companion object {
        private val log = LoggerFactory.getLogger(TestResource::class.java)
    }
}
