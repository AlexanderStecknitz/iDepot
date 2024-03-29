package de.stecknitz.backend.infrastructure.config;

import de.stecknitz.backend.TestUtil;
import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomDaoAuthenticationProviderTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CustomDaoAuthenticationProvider customDaoAuthenticationProvider;

    @Test
    void additionalAuthenticationChecksWithNullCredentialsTest() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null);

        Assert.assertThrows(BadCredentialsException.class, () -> customDaoAuthenticationProvider.additionalAuthenticationChecks(null, authentication));
    }

    @Test
    void additionalAuthenticationChecksWithNullUserTest() {
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(TestUtil.USER_EMAIL)
                .password(TestUtil.USER_PASSWORD)
                .build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, TestUtil.USER_PASSWORD);


        when(userRepository.findByEmail(user.getUsername())).thenReturn(null);
        Assert.assertThrows(NullPointerException.class, () -> customDaoAuthenticationProvider.additionalAuthenticationChecks(user, authentication));
    }

    @Test
    void additionalAuthenticationChecksWithWrongPasswordTest() {

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(TestUtil.USER_EMAIL)
                .password(TestUtil.USER_PASSWORD)
                .build();

        User user = User.builder()
                .email(TestUtil.USER_EMAIL)
                .password(TestUtil.USER_PASSWORD)
                .salt(TestUtil.USER_SALT)
                .build();

        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(Optional.ofNullable(user));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, TestUtil.USER_EMAIL);
        Assert.assertThrows(BadCredentialsException.class, () -> customDaoAuthenticationProvider.additionalAuthenticationChecks(userDetails, authentication));
    }
}
