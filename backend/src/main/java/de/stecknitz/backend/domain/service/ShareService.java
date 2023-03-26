package de.stecknitz.backend.domain.service;

import de.stecknitz.backend.domain.entity.Share;
import de.stecknitz.backend.domain.repository.ShareRepository;
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
        List<Share> shares = shareRepository.findAll();
        shares.forEach(share -> log.debug(share.toString()));
        return shares;
    }

}
