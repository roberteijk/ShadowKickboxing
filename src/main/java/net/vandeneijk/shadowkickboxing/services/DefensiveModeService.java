/**
 * Created by Robert van den Eijk on 27-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.DefensiveMode;
import net.vandeneijk.shadowkickboxing.repositories.DefensiveModeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefensiveModeService {

    private final DefensiveModeRepository defensiveModeRepository;

    public DefensiveModeService(DefensiveModeRepository defensiveModeRepository) {
        this.defensiveModeRepository = defensiveModeRepository;
    }

    public void save(DefensiveMode defensiveMode) {
        defensiveModeRepository.save(defensiveMode);
    }

    public Iterable<DefensiveMode> findAll() {
        return defensiveModeRepository.findAll();
    }

    public Optional<DefensiveMode> findById(Long id) {
        return defensiveModeRepository.findById(id);
    }

    public List<DefensiveMode> getDefensiveModeList() {
        return defensiveModeRepository.findAll();
    }
}
