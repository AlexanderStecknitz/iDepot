package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.web.resources.dto.CompositionPieChartDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompositionPieChartMapper {

    @Mapping(source = "investment.stock.name", target = "investmentName")
    @Mapping(source = "investmentValue", target = "investmentValue")
    CompositionPieChartDTO toCompositionPieChartDTO(Investment investment, double investmentValue);

}
