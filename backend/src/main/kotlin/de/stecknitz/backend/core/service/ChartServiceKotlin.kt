package de.stecknitz.backend.core.service

import de.stecknitz.backend.core.domain.Investment
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTOKotlin
import de.stecknitz.backend.web.resources.dto.mapper.CompositionPieChartMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ChartServiceKotlin(
    private val compositionPieChartMapper: CompositionPieChartMapper,
    private val investmentServiceKotlin: InvestmentServiceKotlin
) {

    fun getCompositionPieChart(depotId: Long): List<CompositionPieChartDTOKotlin> {
        val investments: List<Investment> = investmentServiceKotlin.findByDepotId(depotId)
        if (investments.isEmpty()) {
            return emptyList()
        }
        val accumulatedInvestmentValue: Double = investmentServiceKotlin.accumulateInvestmentValue(depotId)

        return investments.map {
            compositionPieChartMapper.toCompositionPieChartDTO(
                it,
                it.calculateInvestmentValue(accumulatedInvestmentValue)
            )
        }
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(ChartServiceKotlin::class.java)
    }
}