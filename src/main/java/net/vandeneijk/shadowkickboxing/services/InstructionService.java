/**
 * Created by Robert van den Eijk on 17-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.Instruction;
import net.vandeneijk.shadowkickboxing.repositories.InstructionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructionService {

    private final InstructionRepository instructionRepository;

    public InstructionService(InstructionRepository instructionRepository) {
        this.instructionRepository = instructionRepository;
    }

    public void save(Instruction instruction) {
        instructionRepository.save(instruction);
    }

    public Optional<Instruction> findById(Long id) {
        return instructionRepository.findById(id);
    }

    public Iterable<Instruction> findAll() {
        return instructionRepository.findAll();
    }
}
