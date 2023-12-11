package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.UserKotlin;
import de.stecknitz.backend.web.resources.dto.RegisterUserDTOKotlin;
import de.stecknitz.backend.web.resources.dto.UserDTOKotlin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserKotlin toUser(final RegisterUserDTOKotlin registerUserDTO);

    UserDTOKotlin toUserDTO(final UserKotlin user);


}
