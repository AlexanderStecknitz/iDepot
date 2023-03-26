package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.domain.entity.Depot;
import de.stecknitz.backend.domain.entity.Share;
import de.stecknitz.backend.domain.service.DepotService;
import de.stecknitz.backend.web.resources.dto.DepotDTO;
import de.stecknitz.backend.web.resources.dto.ShareDTO;
import de.stecknitz.backend.web.resources.dto.mapper.DepotMapper;
import de.stecknitz.backend.web.resources.dto.mapper.ShareMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depot")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class DepotResource {

    private final DepotService depotService;
    private final DepotMapper depotMapper;
    private final ShareMapper shareMapper;

    @GetMapping("/all")
    public ResponseEntity<List<DepotDTO>> findAll() {
        List<Depot> foundDepots = depotService.findAllDepots();
        if(foundDepots.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        List<DepotDTO> foundDepotsDto = foundDepots.stream().map(depotMapper::toDepoDto).toList();
        return ResponseEntity.ok(foundDepotsDto);
    }

    @GetMapping("/{depotId}/shares")
    public ResponseEntity<List<ShareDTO>> findAllSharesByDepotId(@PathVariable long depotId) {
        List<Share> foundShares = depotService.findAllSharesByDepotId(depotId);
        if(foundShares.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        List<ShareDTO> foundSharesDto = foundShares.stream().map(shareMapper::toShareDto).toList();
        return ResponseEntity.ok(foundSharesDto);
    }

}
