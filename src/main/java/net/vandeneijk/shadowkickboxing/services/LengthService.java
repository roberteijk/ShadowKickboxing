/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.Length;
import net.vandeneijk.shadowkickboxing.repositories.LengthRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LengthService {

    private final LengthRepository lengthRepository;

    public LengthService(LengthRepository lengthRepository) {
        this.lengthRepository = lengthRepository;
    }

    public void save(Length length) {
        lengthRepository.save(length);
    }

    public Iterable<Length> findAll() {
        return lengthRepository.findAll();
    }

    public Optional<Length> findById(Long id) {
        return lengthRepository.findById(id);
    }

    public List<Length> getLengthList() {
        List<Length> lengthList = new ArrayList<>();
        lengthRepository.findAll().forEach(lengthList::add);
        return lengthList;
    }
}
