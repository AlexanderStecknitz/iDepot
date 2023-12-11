package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.Investment;
import de.stecknitz.backend.web.resources.dto.TransactionDTOKotlin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    @Mapping(source = "investment.stock.name", target = "stockName")
    @Mapping(source = "investment.investmentTransactionType", target = "type")
    TransactionDTOKotlin toTransactionDTO(Investment investment, double investmentValue);

}
