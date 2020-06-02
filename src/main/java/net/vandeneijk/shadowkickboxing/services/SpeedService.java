/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.Speed;
import net.vandeneijk.shadowkickboxing.repositories.SpeedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpeedService {

    private final SpeedRepository speedRepository;

    public SpeedService(SpeedRepository speedRepository) {
        this.speedRepository = speedRepository;
    }

    public void save(Speed speed) {
        speedRepository.save(speed);
    }

    public Iterable<Speed> findAll() {
        return speedRepository.findAll();
    }

    public Optional<Speed> findById(Long id) {
        return speedRepository.findById(id);
    }

    public List<Speed> getSpeedList() {
        return speedRepository.findAll();
    }
}
