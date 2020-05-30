/**
 * Created by Robert van den Eijk on 29-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.FightAudioData;
import net.vandeneijk.shadowkickboxing.repositories.FightAudioDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FightAudioDataService {

    private final FightAudioDataRepository fightAudioDataRepository;

    public FightAudioDataService(FightAudioDataRepository fightAudioDataRepository) {
        this.fightAudioDataRepository = fightAudioDataRepository;
    }

    public void save(FightAudioData fightAudioData) {
        fightAudioDataRepository.save(fightAudioData);
    }

    public Optional<FightAudioData> findById(Long id) {
        return fightAudioDataRepository.findById(id);
    }

    public void deleteById(Long id) {
        fightAudioDataRepository.deleteById(id);
    }
}
