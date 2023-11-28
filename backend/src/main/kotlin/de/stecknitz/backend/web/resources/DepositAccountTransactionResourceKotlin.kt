package de.stecknitz.backend.web.resources

import de.stecknitz.backend.core.domain.DepositAccountTransaction
import de.stecknitz.backend.core.service.DepositAccountTransactionService
import de.stecknitz.backend.web.resources.dto.DepositAccountTransactionDTO
import de.stecknitz.backend.web.resources.dto.mapper.DepositAccountTransactionMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/deposit-account-transactions")
class DepositAccountTransactionResourceKotlin(
    private val depositAccountTransactionService: DepositAccountTransactionService,
    private val depositAccountTransactionMapper: DepositAccountTransactionMapper
) {

    @GetMapping(path = ["/{depositAccountId}"])
    fun getByDepositAccountId(@PathVariable depositAccountId: Long): ResponseEntity<List<DepositAccountTransactionDTO>> {
        val depositAccountTransactions: List<DepositAccountTransaction> =
            depositAccountTransactionService.findByDepositAccountId(depositAccountId)

        val depositAccountTransactionDTOs: List<DepositAccountTransactionDTO> =
            depositAccountTransactions.map { depositAccountTransactionMapper.toDepositAccountTransactionDTO(it) }

        return ResponseEntity.ok(depositAccountTransactionDTOs)
    }

}