package de.stecknitz.backend.web.resources.dto

import de.stecknitz.backend.core.domain.Depot

data class DepotDTOKotlin(
    val id: Long
) {
    companion object {
        fun toDepot(depotDTOKotlin: DepotDTOKotlin): Depot = Depot(depotDTOKotlin.id, null)
    }
}
