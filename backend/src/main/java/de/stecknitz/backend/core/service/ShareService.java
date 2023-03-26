package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.core.repository.ShareRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareService {

    private final ShareRepository shareRepository;

    @Transactional
    public List<Share> findAll() {
        return shareRepository.findAll();
    }

}
