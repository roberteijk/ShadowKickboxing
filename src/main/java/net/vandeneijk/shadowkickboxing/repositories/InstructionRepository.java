/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.Instruction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InstructionRepository extends CrudRepository<Instruction, Long> {

    Optional<Instruction> findByDescription(String description);
}
