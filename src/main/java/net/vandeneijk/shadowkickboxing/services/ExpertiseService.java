/**
 * Created by Robert van den Eijk on 1-6-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.Expertise;
import net.vandeneijk.shadowkickboxing.repositories.ExpertiseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertiseService {

    private final ExpertiseRepository expertiseRepository;

    public ExpertiseService(ExpertiseRepository expertiseRepository) {
        this.expertiseRepository = expertiseRepository;
    }

    public void save(Expertise expertise) {
        expertiseRepository.save(expertise);
    }

    public Iterable<Expertise> findAll() {
        return expertiseRepository.findAll();
    }

    public Optional<Expertise> findById(Long id) {
        return expertiseRepository.findById(id);
    }

    public List<Expertise> getExpertiseList() {
        return expertiseRepository.findAll();
    }
}
