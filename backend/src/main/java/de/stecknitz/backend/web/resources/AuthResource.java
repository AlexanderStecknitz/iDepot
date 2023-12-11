package de.stecknitz.backend.web.resources;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.UserKotlin;
import de.stecknitz.backend.core.service.UserServiceKotlin;
import de.stecknitz.backend.web.resources.dto.LoginResultDTOKotlin;
import de.stecknitz.backend.web.resources.dto.RegisterUserDTOKotlin;
import de.stecknitz.backend.web.resources.dto.UserDTOKotlin;
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
import org.springframework.web.bind.annotation.PathVariable;
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

    private final UserServiceKotlin userService;

    private final UserMapper userMapper;

    private final JwtEncoder jwtEncoder;

    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping(path = "/register")
    ResponseEntity<Void> register(@RequestBody RegisterUserDTOKotlin registerUserDTO) {
        UserKotlin user = userMapper.toUser(registerUserDTO);
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResultDTOKotlin> token(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .build();

        LoginResultDTOKotlin loginResultDTO = new LoginResultDTOKotlin(
                jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(),
                authentication.getName(),
                userService.findByEmail(authentication.getName()).getDepots().stream().map(Depot::getId).toList()
        );
//                LoginResultDTO.builder()
//                .token(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
//                .email(authentication.getName())
//                .depotId(userService.findByEmail(authentication.getName()).getDepots().stream().map(Depot::getId).toList())
//                .build();

        return ResponseEntity.ok(loginResultDTO);
    }

    @GetMapping(path = "/user/{email}")
    public ResponseEntity<UserDTOKotlin> findUser(@PathVariable final String email) {
        UserKotlin foundUser = userService.findByEmail(email);
        UserDTOKotlin userDTO = userMapper.toUserDTO(foundUser);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping(path = "/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "logout from: " + authentication.getName();
    }
}
