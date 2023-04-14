package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.SharePosition;
import de.stecknitz.backend.web.resources.dto.SharePositionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SharePositionMapper {

    @Mapping(source = "depotId", target = "depot.id")
    @Mapping(source = "share.isin", target = "share.isin")
    SharePosition toSharePosition(SharePositionDTO sharePositionDTO);

    @Mapping(source = "depot.id", target = "depotId")
    @Mapping(source = "share.isin", target = "share.isin")
    SharePositionDTO toSharePositionDTO(SharePosition sharePosition);

}
