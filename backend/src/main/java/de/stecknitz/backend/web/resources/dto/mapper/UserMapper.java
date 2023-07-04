package de.stecknitz.backend.web.resources.dto.mapper;

import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.web.resources.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "role", target = "role.name")
    User toUser(UserDTO userDTO);

}
