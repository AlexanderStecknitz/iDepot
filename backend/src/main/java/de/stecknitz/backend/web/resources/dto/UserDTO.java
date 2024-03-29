package de.stecknitz.backend.web.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class UserDTO {

    String email;
    String firstname;
    String lastname;

}
