package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.service.DepotService;
import de.stecknitz.backend.web.resources.dto.DepotDTO;
import de.stecknitz.backend.web.resources.dto.mapper.DepotMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/depot")
@RequiredArgsConstructor
@Slf4j
public class DepotResource {

    private final DepotService depotService;
    private final DepotMapper depotMapper;

    @GetMapping
    public ResponseEntity<List<DepotDTO>> findAll() {
        List<Depot> foundDepots = depotService.findAllDepots();
        if (foundDepots.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<DepotDTO> foundDepotsDto = foundDepots.stream().map(depotMapper::toDepotDTO).toList();
        return ResponseEntity.ok(foundDepotsDto);
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<List<DepotDTO>> findAllByEmail(
            @PathVariable String email
    ) {
        List<Depot> foundDepots = depotService.findAllByEmail(email);
        if (foundDepots.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<DepotDTO> foundDepotsDto = foundDepots.stream().map(depotMapper::toDepotDTO).toList();
        return ResponseEntity.ok(foundDepotsDto);
    }

    @PostMapping(path = "/{email}")
    public ResponseEntity<Void> create(@PathVariable String email) {
        log.debug(email);
        depotService.create(email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
