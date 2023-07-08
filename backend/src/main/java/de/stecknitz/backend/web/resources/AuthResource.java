package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.service.UserService;
import de.stecknitz.backend.web.resources.dto.LoginResultDTO;
import de.stecknitz.backend.web.resources.dto.RegisterUserDTO;
import de.stecknitz.backend.web.resources.dto.UserDTO;
import de.stecknitz.backend.web.resources.dto.mapper.DepotMapper;
import de.stecknitz.backend.web.resources.dto.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthResource {

    private final UserService userService;

    private final UserMapper userMapper;

    private final DepotMapper depotMapper;

    private final JwtEncoder jwtEncoder;

    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping(path = "/register")
    ResponseEntity<Void> register(@RequestBody RegisterUserDTO registerUserDTO) {
        User user = userMapper.toUser(registerUserDTO);
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResultDTO> token(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .build();

        User user = userService.findByEmail(authentication.getName());

        LoginResultDTO token = LoginResultDTO.builder()
                .token(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
                .depots(user.getDepots().stream().map(depotMapper::toDepotDTO).toList())
                .build();

        return ResponseEntity.ok(token);
    }

    @GetMapping(path = "/user")
    public ResponseEntity<UserDTO> findUser(Authentication authentication) {
        User foundUser = userService.findByEmail(authentication.getName());
        UserDTO userDTO = userMapper.toUserDTO(foundUser);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping(path = "/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "logout from: " + authentication.getName();
    }
}
