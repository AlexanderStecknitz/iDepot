package de.stecknitz.backend.core.service;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.UserRepository;
import de.stecknitz.backend.core.service.exception.UserAlreadyExistsException;
import de.stecknitz.backend.core.service.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void findByEmailTest() {
        final String email = TestUtil.USER_EMAIL;

        User user = User.builder()
                .email(email)
                .build();

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        User foundUser = userService.findByEmail(email);

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
        User user = User.builder()
                .email(TestUtil.USER_EMAIL)
                .build();

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        Assert.assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
    }

}
