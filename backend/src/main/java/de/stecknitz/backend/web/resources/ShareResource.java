package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.domain.entity.Share;
import de.stecknitz.backend.domain.service.ShareService;
import de.stecknitz.backend.web.resources.dto.ShareDTO;
import de.stecknitz.backend.web.resources.dto.mapper.ShareMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/share")
@RequiredArgsConstructor
@Slf4j
public class ShareResource {

    private final ShareService shareService;
    private final ShareMapper shareMapper;

    @GetMapping("/all")
    public ResponseEntity<List<ShareDTO>> findAll() {
        log.debug("findAll");
        List<Share> foundShares = shareService.findAll();
        List<ShareDTO> foundSharesDto = foundShares.stream().map(shareMapper::toShareDto).toList();
        return ResponseEntity.ok(foundSharesDto);
    }

}
