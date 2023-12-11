package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.UserKotlin;
import de.stecknitz.backend.core.repository.UserRepositoryKotlin;
import de.stecknitz.backend.core.service.exception.UserAlreadyExistsException;
import de.stecknitz.backend.core.service.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepositoryKotlin userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    DepotServiceKotlin depotServiceKotlin;

    @InjectMocks
    UserServiceKotlin userService;

    @Test
    void findByEmailTest() {
        final String email = TestUtil.USER_EMAIL;

        UserKotlin user = new UserKotlin(email, "", "", "", null, "", null);

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        UserKotlin foundUser = userService.findByEmail(email);

        Assertions.assertThat(foundUser.getEmail()).isEqualTo(email);

    }

    @Test
    void findByEmailNotFoundTest() {
        final String email = TestUtil.USER_EMAIL;

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        Assert.assertThrows(UserNotFoundException.class, () -> userService.findByEmail(email));

    }

    @Test
    void createWithUserAlreadyExistsTest() {
        UserKotlin user = new UserKotlin(TestUtil.USER_EMAIL, "", "", "", null, "", null);

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        Assert.assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
    }

}
