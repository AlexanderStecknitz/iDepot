package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.User;
import de.stecknitz.backend.core.repository.UserRepository;
import de.stecknitz.backend.core.service.exception.ErrorConstants;
import de.stecknitz.backend.core.service.exception.MasterDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()) {
            throw new MasterDataException(ErrorConstants.USER_ALREADY_EXISTS);
        }
        String salt = generateSalt();
        user.setSalt(salt);
        user.setPassword(passwordEncoder.encode(user.getPassword() + salt));
        userRepository.save(user);

    }

    private String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
     }

}
