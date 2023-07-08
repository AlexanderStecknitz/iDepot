package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.web.resources.dto.RegisterUserDTO;
import de.stecknitz.backend.web.resources.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUser(final RegisterUserDTO registerUserDTO);

    UserDTO toUserDTO(final User user);


}
