package de.stecknitz.backend.core.repository

import de.stecknitz.backend.core.domain.Depot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepotRepositoryKotlin : JpaRepository<Depot, Long> {

    fun findAllByUserEmail(email: String): List<Depot>

}