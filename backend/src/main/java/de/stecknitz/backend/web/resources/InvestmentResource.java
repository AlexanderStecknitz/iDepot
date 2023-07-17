package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.core.service.InvestmentService;
import de.stecknitz.backend.web.resources.dto.InvestmentDTO;
import de.stecknitz.backend.web.resources.dto.mapper.InvestmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/investment")
@RequiredArgsConstructor
@Slf4j
public class InvestmentResource {

    private final InvestmentService investmentService;
    private final InvestmentMapper investmentMapper;

    @GetMapping
    public ResponseEntity<List<InvestmentDTO>> findAll() {
        log.debug("findAll");
        List<Investment> investments = investmentService.findAll();
        if(investments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<InvestmentDTO> investmentDTOS = investments.stream().map(investmentMapper::toInvestmentDTO).toList();
        return ResponseEntity.ok(investmentDTOS);
    }

    @GetMapping(path = "/{depotId}")
    public ResponseEntity<List<InvestmentDTO>> findStockInDepot(
            @PathVariable final long depotId) {
        List<Investment> investments = investmentService.findByDepotId(depotId);
        if(investments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(investments.stream().map(investmentMapper::toInvestmentDTO).toList());
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody final InvestmentDTO investmentDTO) {
        log.debug("create: position={}", investmentDTO);
        Investment resultInvestment = investmentService.create(investmentMapper.toInvestment(investmentDTO));
        if(resultInvestment == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
