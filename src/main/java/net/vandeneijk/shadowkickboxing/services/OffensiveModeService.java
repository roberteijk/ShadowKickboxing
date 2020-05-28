/**
 * Created by Robert van den Eijk on 27-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.OffensiveMode;
import net.vandeneijk.shadowkickboxing.repositories.OffensiveModeRepository;
import org.springframework.stereotype.Service;

@Service
public class OffensiveModeService {

    private final OffensiveModeRepository offensiveModeRepository;

    public OffensiveModeService(OffensiveModeRepository offensiveModeRepository) {
        this.offensiveModeRepository = offensiveModeRepository;
    }

    public void save(OffensiveMode offensiveMode) {
        offensiveModeRepository.save(offensiveMode);
    }
}
