package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.web.resources.dto.InvestmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestmentMapper {

    @Mapping(source = "depotId", target = "depot.id")
    @Mapping(source = "stockDTO.isin", target = "stock.isin")
    Investment toInvestment(InvestmentDTO investmentDTO);

    @Mapping(source = "depot.id", target = "depotId")
    @Mapping(source = "stock.isin", target = "stockDTO.isin")
    InvestmentDTO toInvestmentDTO(Investment sharePosition);

}
