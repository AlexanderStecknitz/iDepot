package de.stecknitz.backend.core.service;

import de.stecknitz.backend.core.domain.Depot;
import de.stecknitz.backend.core.domain.Share;
import de.stecknitz.backend.core.domain.SharePosition;
import de.stecknitz.backend.core.repository.DepotRepository;
import de.stecknitz.backend.core.repository.SharePositionRepository;
import de.stecknitz.backend.core.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SharePositionService {

    private final SharePositionRepository sharePositionRepository;
    private final DepotRepository depotRepository;
    private final ShareRepository shareRepository;

    @Transactional(readOnly = true)
    public List<SharePosition> findAll() { return sharePositionRepository.findAll(); }

    @Transactional(readOnly = true)
    public List<SharePosition> findByDepotId(final long depotId) {
        Optional<List<SharePosition>> optionalDepot = sharePositionRepository.findByDepotId(depotId);
        return optionalDepot.orElse(Collections.emptyList());
    }

    @Transactional
    public SharePosition create(final SharePosition sharePosition) {
        log.debug("SharePositionService={}", sharePosition);
        Optional<Depot> optionalDepot = depotRepository.findById(sharePosition.getDepot().getId());
        if(optionalDepot.isEmpty()) {
            return null;
        }
        Optional<Share> optionalShare = shareRepository.findById(sharePosition.getShare().getIsin());
        if(optionalShare.isEmpty()) {
            Share share = Share.builder()
                    .isin(sharePosition.getShare().getIsin())
                    .build();
            shareRepository.saveAndFlush(share);
        }
        return sharePositionRepository.saveAndFlush(sharePosition);
    }

}
