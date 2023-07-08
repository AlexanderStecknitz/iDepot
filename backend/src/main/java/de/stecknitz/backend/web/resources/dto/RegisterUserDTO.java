package de.stecknitz.backend.web.resources.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class RegisterUserDTO {
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
