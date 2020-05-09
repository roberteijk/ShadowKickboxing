/**
 * Created by Robert van den Eijk on 6-5-2020.
 */

package net.vandeneijk.shadowkickboxing.fightfactory;

import net.vandeneijk.shadowkickboxing.models.Audio;
import net.vandeneijk.shadowkickboxing.models.Instruction;
import net.vandeneijk.shadowkickboxing.models.Language;
import net.vandeneijk.shadowkickboxing.repositories.AudioRepository;
import net.vandeneijk.shadowkickboxing.repositories.InstructionRepository;
import net.vandeneijk.shadowkickboxing.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


@Component
@DependsOn("seedDatabase")
public class FightFactory {


    private final InstructionRepository instructionRepository;
    private final LanguageRepository languageRepository;
    private final AudioRepository audioRepository;

    private List<Long> instructionCallWeightDistribution;
    private int silenceLengthMillis;
    private Audio audioSilence;


    @Autowired
    public FightFactory(InstructionRepository instructionRepository, LanguageRepository languageRepository, AudioRepository audioRepository, LanguageRepository languageRepository1, AudioRepository audioRepository1) {
        this.instructionRepository = instructionRepository;
        this.languageRepository = languageRepository1;
        this.audioRepository = audioRepository1;

        seedInstructionCallWeightDistribution();
        getAudioSilence();
        createFight(1, 1);
    }

    public void createFight(int numberOfRounds, long languageId) {
        List<List<Byte>> rounds = new ArrayList<>();

        while (rounds.size() < numberOfRounds) {
            rounds.add(createRound(languageId));
        }

        System.out.println("Size of a round : " + rounds.get(0).size());

        byte[] testByte = new byte[rounds.get(0).size()];
        int j = 0;
        for (byte value : rounds.get(0)) testByte[j++] = value;



        try (FileOutputStream fos = new FileOutputStream("test.mp3")) {
            fos.write(testByte);
            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<Byte> createRound(long languageId) {
        List<Byte> round = new ArrayList<>();
        int roundLengthMillis = 180_000;
        while (true) {
            long moveToAdd = instructionCallWeightDistribution.get((int) (Math.random() * instructionCallWeightDistribution.size()));

            Instruction instruction = instructionRepository.findById(moveToAdd).get();
            Language language = languageRepository.findById(languageId).get();
            Audio audio = audioRepository.findByInstructionAndLanguage(instruction, language);

            Move move = createMove(audio, instruction.getMinExecutionTimeMillis(), instruction.getMaxExecutionTimeMillis(), roundLengthMillis);
            if (move == null) break;
            round.addAll(move.getMoveAudio());
            roundLengthMillis -= move.getMoveAudioLengthMillis();
        }
        return round;
    }

    private Move createMove(Audio audio, int minExecutionTimeMillis, int maxExecutionTimeMillis, int roundLengthMillisRemaining) {
        if (audio.getLengthMillis() + minExecutionTimeMillis > roundLengthMillisRemaining) return null;

        List<Byte> move = new ArrayList<>();
        int oldRoundLengthMillisRemaining = roundLengthMillisRemaining;
        for (byte value : audio.getAudioFragment()) move.add(value);
        roundLengthMillisRemaining -= audio.getLengthMillis();

        int executionTimeMillis = (int) ((Math.random() * (maxExecutionTimeMillis - minExecutionTimeMillis)) + minExecutionTimeMillis);
        if (executionTimeMillis > roundLengthMillisRemaining) executionTimeMillis = roundLengthMillisRemaining;
        int numberOfSilencesToAdd = (int) Math.ceil(executionTimeMillis / (double) silenceLengthMillis);

        for (int i = 0; i < numberOfSilencesToAdd; i++) for (byte value : audioSilence.getAudioFragment()) move.add(value);
        roundLengthMillisRemaining -= numberOfSilencesToAdd * silenceLengthMillis;

        return new Move(move, oldRoundLengthMillisRemaining - roundLengthMillisRemaining);
    }


    private void seedInstructionCallWeightDistribution() {
        instructionCallWeightDistribution = new ArrayList<>();
        for (Instruction instruction : instructionRepository.findAll()) {
            if (instruction.getInstructionId() < 100L) continue;
            for (int i = 0; i < instruction.getCallFrequencyWeight() * 100; i++) {
                instructionCallWeightDistribution.add(instruction.getInstructionId());
            }
        }
    }

    private void getAudioSilence() {
        Instruction instruction = instructionRepository.findById(0L).get();
        Language language = languageRepository.findById(0L).get();
        audioSilence = audioRepository.findByInstructionAndLanguage(instruction, language);
        silenceLengthMillis = audioSilence.getLengthMillis();
    }

}

