package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.service.UserService;
import de.stecknitz.backend.web.resources.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping(path = "/register")
    ResponseEntity<Void> register(@RequestBody UserDTO userDTO) {
        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(userDTO.getPassword())
                .build();
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
