package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.DepositAccountTransaction;
import de.stecknitz.backend.web.resources.dto.DepositAccountTransactionDTOKotlin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepositAccountTransactionMapper {

    DepositAccountTransactionDTOKotlin toDepositAccountTransactionDTO(DepositAccountTransaction depositAccountTransaction);

}
