package de.stecknitz.backend.core.repository

import de.stecknitz.backend.core.domain.Investment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvestmentRepositoryKotlin : JpaRepository<Investment, Long> {
    fun findByDepotId(depotId: Long): List<Investment>?
}