/**
 * Created by Robert van den Eijk on 1-6-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.BodyHalf;
import net.vandeneijk.shadowkickboxing.repositories.BodyHalfRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodyHalfService {

    private final BodyHalfRepository bodyHalfRepository;

    public BodyHalfService(BodyHalfRepository bodyHalfRepository) {
        this.bodyHalfRepository = bodyHalfRepository;
    }

    public void save(BodyHalf bodyHalf) {
        bodyHalfRepository.save(bodyHalf);
    }

    public Iterable<BodyHalf> findAll() {
        return bodyHalfRepository.findAll();
    }

    public Optional<BodyHalf> findById(Long id) {
        return bodyHalfRepository.findById(id);
    }
//    public BodyHalf findById(Long id) {
//        return bodyHalfRepository.findById(id);
//    }

    public List<BodyHalf> getBodyHalfList() {
        return bodyHalfRepository.findAll();
    }
}
