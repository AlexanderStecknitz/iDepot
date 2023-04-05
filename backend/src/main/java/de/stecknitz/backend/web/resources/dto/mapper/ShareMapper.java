package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.web.resources.dto.ShareDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ShareMapper {

    ShareDTO toShareDto(Share share);

    Share toShare(ShareDTO shareDTO);

}
