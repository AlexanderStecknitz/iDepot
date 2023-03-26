package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.web.resources.dto.DepotDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DepotMapper {
    DepotDTO toDepoDto(Depot depot);
}
