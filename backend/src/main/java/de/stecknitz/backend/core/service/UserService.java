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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    //private String generateSalt() throws NoSuchAlgorithmException {
    //     Random random = SecureRandom.getInstanceStrong();
    //     StringBuilder stringBuilder = new StringBuilder();
    //     int wordLength = random.nextInt(0,19);
    //     for (int i = 0; i < wordLength; i++) {
    //         int nextChar = random.nextInt(0,27);
    //         char c = (char) ('A' + nextChar);
    //         stringBuilder.append(c);
    //     }
    //     return stringBuilder.toString();
    // }

}
