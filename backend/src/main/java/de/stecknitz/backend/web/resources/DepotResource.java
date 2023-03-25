package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.domain.entity.Depot;
import de.stecknitz.backend.domain.service.DepotService;
import de.stecknitz.backend.web.resources.dto.DepotDTO;
import de.stecknitz.backend.web.resources.dto.mapper.DepotMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/all")
    public ResponseEntity<List<DepotDTO>> findAll() {
        List<Depot> foundDepots = depotService.findAllDepots();
        if(foundDepots.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        List<DepotDTO> foundDepotsDto = foundDepots.stream().map(depotMapper::toDepoDto).toList();
        return ResponseEntity.ok(foundDepotsDto);
    }

}
