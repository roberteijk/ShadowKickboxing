/**
 * Created by Robert van den Eijk on 22-6-2020.
 */

package net.vandeneijk.shadowkickboxing.services.fightfactoryservice;

import net.vandeneijk.shadowkickboxing.models.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FightFactoryJob {

    private final String languageDescription;
    private final Set<Instruction> instructions = new HashSet<>();
    private final Expertise expertise;
    private final DefensiveMode defensiveMode;
    private final Set<InstructionPreFilter> instructionPreFilters = new HashSet<>();
    private final Speed speed;
    private final Length length;
    private final Set<InstructionPostModifier> instructionPostModifiers = new HashSet<>();
    private final List<Long> instructionIdWeightDistribution = new ArrayList<>();

    public FightFactoryJob(String languageDescription, Iterable<Instruction> instructions, Expertise expertise, DefensiveMode defensiveMode, Speed speed, Length length) {
        this.languageDescription = languageDescription;
        instructions.forEach(this.instructions::add);
        this.expertise = expertise;
        this.defensiveMode = defensiveMode;
        instructionPreFilters.add(expertise);
        instructionPreFilters.add(defensiveMode);
        this.speed = speed;
        this.length = length;
        instructionPostModifiers.add(speed);
        instructionPostModifiers.add(length);

        applyPreFilters();
        seedInstructionCallWeightDistribution();
    }

    private void applyPreFilters() {
        instructionPreFilters.forEach(x -> x.filter(instructions));
    }

    private synchronized void seedInstructionCallWeightDistribution() {
        for (Instruction instruction : instructions) {
            if (!instruction.isMove()) continue;
            for (int i = 0; i < instruction.getCallFrequencyWeight() * 1000; i++) {
                instructionIdWeightDistribution.add(instruction.getInstructionId());
            }
        }
    }

    public String getLanguageDescription() {
        return languageDescription;
    }

    public Expertise getExpertise() {
        return expertise;
    }

    public DefensiveMode getDefensiveMode() {
        return defensiveMode;
    }

    public Speed getSpeed() {
        return speed;
    }

    public Length getLength() {
        return length;
    }

    public long getRandomInstructionId() {
        return instructionIdWeightDistribution.get((int) ((Math.random() * instructionIdWeightDistribution.size())));
    }
}
