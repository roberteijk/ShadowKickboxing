/**
 * Created by Robert van den Eijk on 6-5-2020.
 */

package net.vandeneijk.shadowkickboxing.fightfactory;

import net.vandeneijk.shadowkickboxing.models.Instruction;
import net.vandeneijk.shadowkickboxing.repositories.InstructionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@DependsOn("seedDatabase")
public class FightFactory {


    private InstructionRepository instructionRepository;

    private List<Long> instructionCallWeightDistribution = new ArrayList<>();


    @Autowired
    public FightFactory(InstructionRepository instructionRepository) {
        this.instructionRepository = instructionRepository;
        seedInstructionCallWeightDistribution();
    }

    public void createFight(int numberOfRounds) {
        List<Byte[]> rounds = new ArrayList<>();

        while (rounds.size() < numberOfRounds) {
            rounds.add(createRound());
        }
    }

    private Byte[] createRound() {
        return new Byte[0];
    }


    private void seedInstructionCallWeightDistribution() {
        for (Instruction instruction : instructionRepository.findAll()) {
            for (int i = 0; i < instruction.getCallFrequencyWeight() * 100; i++) {
                instructionCallWeightDistribution.add(instruction.getInstructionId());
            }
        }
    }

}

