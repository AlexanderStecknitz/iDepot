package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.web.resources.dto.DepotDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepotMapper {

    DepotDTO toDepoDto(Depot depot);

    Depot toDepo(DepotDTO depotDTO);
}
