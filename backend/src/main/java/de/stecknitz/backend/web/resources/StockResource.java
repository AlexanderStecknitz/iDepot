package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.Stock;
import de.stecknitz.backend.core.service.StockService;
import de.stecknitz.backend.web.resources.dto.StockDTO;
import de.stecknitz.backend.web.resources.dto.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@Slf4j
public class StockResource {

    private final StockService stockService;
    private final StockMapper stockMapper;

    @GetMapping
    public ResponseEntity<List<StockDTO>> findAll() {
        log.debug("findAll");
        List<Stock> foundStocks = stockService.findAll();
        if(foundStocks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<StockDTO> foundSharesDto = foundStocks.stream().map(stockMapper::toStockDto).toList();
        return ResponseEntity.ok(foundSharesDto);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody final StockDTO stockDTO
    ) {
        Stock stock = stockService.create(stockMapper.toStock(stockDTO));
        if(stock == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
