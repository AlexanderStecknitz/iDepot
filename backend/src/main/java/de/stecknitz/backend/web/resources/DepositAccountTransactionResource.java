package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.DepositAccountTransaction;
import de.stecknitz.backend.core.service.DepositAccountTransactionService;
import de.stecknitz.backend.web.resources.dto.DepositAccountTransactionDTO;
import de.stecknitz.backend.web.resources.dto.mapper.DepositAccountTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/deposit-account-transactions")
@RequiredArgsConstructor
public class DepositAccountTransactionResource {

    private final DepositAccountTransactionService depositAccountTransactionService;

    private final DepositAccountTransactionMapper depositAccountTransactionMapper;

    @GetMapping(path = "/{depositAccountId}")
    ResponseEntity<List<DepositAccountTransactionDTO>> getByDepositAccountId(
            @PathVariable final long depositAccountId
    ) {
        List<DepositAccountTransaction> depositAccountTransactions = depositAccountTransactionService.findByDepositAccountId(depositAccountId);
        List<DepositAccountTransactionDTO> depositAccountTransactionDTOs = depositAccountTransactions.stream()
                .map(depositAccountTransactionMapper::toDepositAccountTransactionDTO)
                .toList();
        return ResponseEntity.ok(depositAccountTransactionDTOs);
    }

}
