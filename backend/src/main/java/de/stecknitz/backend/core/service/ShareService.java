package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.core.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareService {

    private final ShareRepository shareRepository;

    @Transactional(readOnly = true)
    public List<Share> findAll() {
        return shareRepository.findAll();
    }

    @Transactional
    public Share create(final Share share) {
        Optional<Share> optionalShare = shareRepository.findById(share.getIsin());
        if(optionalShare.isPresent()) {
            return null;
        }
        return shareRepository.saveAndFlush(share);
    }
}
