package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.SharePosition;
import de.stecknitz.backend.core.service.SharePositionService;
import de.stecknitz.backend.web.resources.dto.SharePositionDTO;
import de.stecknitz.backend.web.resources.dto.mapper.SharePositionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/position/share")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class SharePositionResource {

    private final SharePositionService sharePositionService;
    private final SharePositionMapper sharePositionMapper;

    @GetMapping
    public ResponseEntity<List<SharePositionDTO>> findAll() {
        log.debug("findAll");
        List<SharePosition> sharePositions = sharePositionService.findAll();
        if(sharePositions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SharePositionDTO> sharePositionDTOS = sharePositions.stream().map(sharePositionMapper::toSharePositionDTO).toList();
        return ResponseEntity.ok(sharePositionDTOS);
    }

    @GetMapping(path = "/{depotId}")
    public ResponseEntity<List<SharePosition>> findSharesInDepot(
            @PathVariable final long depotId
    ) {
        List<SharePosition> sharePositions = sharePositionService.findByDepotId(depotId);
        if(sharePositions.isEmpty()) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sharePositions);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody final SharePositionDTO sharePositionDTO) {

        log.debug("create: position={}", sharePositionDTO);
        SharePosition resultSharePosition = sharePositionService.create(sharePositionMapper.toSharePosition(sharePositionDTO));
        if(resultSharePosition == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

}
