package de.stecknitz.backend.core.repository

import de.stecknitz.backend.core.domain.StockKotlin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockRepositoryKotlin : JpaRepository<StockKotlin, String> {

    fun findByIsin(isin: String): StockKotlin?

}