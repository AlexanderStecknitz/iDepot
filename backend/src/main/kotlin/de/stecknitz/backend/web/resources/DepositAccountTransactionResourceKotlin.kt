package de.stecknitz.backend.web.resources

import de.stecknitz.backend.core.domain.DepositAccountTransaction
import de.stecknitz.backend.core.service.DepositAccountTransactionServiceKotlin
import de.stecknitz.backend.web.resources.dto.DepositAccountTransactionDTOKotlin
import de.stecknitz.backend.web.resources.dto.mapper.DepositAccountTransactionMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/deposit-account-transactions")
class DepositAccountTransactionResourceKotlin(
    private val depositAccountTransactionService: DepositAccountTransactionServiceKotlin,
    private val depositAccountTransactionMapper: DepositAccountTransactionMapper
) {

    @GetMapping(path = ["/{depositAccountId}"])
    fun getByDepositAccountId(@PathVariable depositAccountId: Long): ResponseEntity<List<DepositAccountTransactionDTOKotlin>> {
        val depositAccountTransactions: List<DepositAccountTransaction> =
            depositAccountTransactionService.findByDepositAccountId(depositAccountId)

        val depositAccountTransactionDTOs: List<DepositAccountTransactionDTOKotlin> =
            depositAccountTransactions.map { depositAccountTransactionMapper.toDepositAccountTransactionDTO(it) }

        return ResponseEntity.ok(depositAccountTransactionDTOs)
    }

}