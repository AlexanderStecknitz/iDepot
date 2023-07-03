package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.web.resources.dto.DepotDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DepotMapper {

    Depot toDepot(DepotDTO depotDTO);

    DepotDTO toDepotDTO(Depot depot);

}
