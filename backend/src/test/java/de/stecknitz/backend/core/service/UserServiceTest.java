package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.UserRepository;
import de.stecknitz.backend.core.service.exception.MasterDataException;
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
    void createWithUserAlreadyExistsTest() {
        User user = User.builder()
                .email("admin")
                .build();

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        Assert.assertThrows(MasterDataException.class, () -> userService.create(user));
    }

}
