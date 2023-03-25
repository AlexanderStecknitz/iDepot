package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.domain.entity.Share;
import de.stecknitz.backend.web.resources.dto.ShareDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ShareMapper {

    ShareDTO toShareDto(Share share);

}