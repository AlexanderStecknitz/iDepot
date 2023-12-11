package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.web.resources.dto.InvestmentDTOKotlin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestmentMapper {

    @Mapping(source = "depotId", target = "depot.id")
    @Mapping(source = "isin", target = "stock.isin")
    Investment toInvestment(InvestmentDTOKotlin investmentDTO);

    @Mapping(source = "depot.id", target = "depotId")
    @Mapping(source = "stock.isin", target = "isin")
    @Mapping(source = "stock.currentPrice", target = "currentPrice")
    @Mapping(source = "stock.name", target = "name")
    @Mapping(source = "investmentTransactionType", target = "transactionType")
    InvestmentDTOKotlin toInvestmentDTO(Investment investment);

}
