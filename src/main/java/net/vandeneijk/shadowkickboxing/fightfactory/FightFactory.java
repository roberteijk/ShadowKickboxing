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
        createFight(1, 180, 1);
    }

    public void createFight(int numberOfRounds, int roundLengthSeconds, long languageId) {
        List<List<Byte>> rounds = new ArrayList<>();

        while (rounds.size() < numberOfRounds) {
            rounds.add(createRound(roundLengthSeconds, languageId));
        }


        // Below in this method is for debugging purposes only.
        System.out.println("Size of a round : " + rounds.get(0).size());

        byte[] testByte = new byte[rounds.get(0).size()];
        int j = 0;
        for (byte value : rounds.get(0)) testByte[j++] = value;

        try (FileOutputStream fos = new FileOutputStream("test.mp3")) {
            fos.write(testByte);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<Byte> createRound(int roundLengthSeconds, long languageId) {
        List<Byte> round = new ArrayList<>();
        int roundLengthMillisRemaining = roundLengthSeconds * 1000;
        while (true) {
            Language language = languageRepository.findById(languageId).get();

            Move move = createMove(language, roundLengthMillisRemaining);
            if (move == null) break;

            prependWithBlockIfApplicable(move, language, roundLengthMillisRemaining);
            prependWithInnerRoundBreakIfApplicable(move, roundLengthMillisRemaining);

            round.addAll(move.getBytesListAudioMove());
            roundLengthMillisRemaining -= move.getTotalMoveAudioLengthMillis();
        }
        return round;
    }

    private Move createMove(Language language, int roundLengthMillisRemaining) {
        long moveToAdd = instructionCallWeightDistribution.get((int) (Math.random() * instructionCallWeightDistribution.size()));

        Instruction instructionMove = instructionRepository.findById(moveToAdd).get();
        Audio audioMove = audioRepository.findByInstructionAndLanguage(instructionMove, language);
        int minExecutionTimeMillis = instructionMove.getMinExecutionTimeMillis();
        int maxExecutionTimeMillis = instructionMove.getMaxExecutionTimeMillis();

        if (audioMove.getLengthMillis() + minExecutionTimeMillis > roundLengthMillisRemaining) return null;

        List<Byte> bytesListAudioMove = new ArrayList<>();
        int oldRoundLengthMillisRemaining = roundLengthMillisRemaining;
        for (byte value : audioMove.getAudioFragment()) bytesListAudioMove.add(value);
        roundLengthMillisRemaining -= audioMove.getLengthMillis();

        int executionTimeMillis = (int) ((Math.random() * (maxExecutionTimeMillis - minExecutionTimeMillis)) + minExecutionTimeMillis);
        if (executionTimeMillis > roundLengthMillisRemaining) executionTimeMillis = roundLengthMillisRemaining;
        int numberOfSilencesToAdd = (int) Math.ceil(executionTimeMillis / (double) silenceLengthMillis);

        for (int i = 0; i < numberOfSilencesToAdd; i++) for (byte value : audioSilence.getAudioFragment()) bytesListAudioMove.add(value);
        roundLengthMillisRemaining -= numberOfSilencesToAdd * silenceLengthMillis;

        return new Move(bytesListAudioMove, oldRoundLengthMillisRemaining - roundLengthMillisRemaining, instructionMove);
    }

    private void prependWithBlockIfApplicable(Move move, Language language, int roundLengthMillisRemaining) {
        if (!move.getOriginalInstruction().isCanBlock() && !move.getOriginalInstruction().isCanEvade()) return;
        else if (Math.random() < 0.80) return;

        long defensiveInstruction;
        if (move.getOriginalInstruction().isCanBlock() && !move.getOriginalInstruction().isCanEvade()) defensiveInstruction = 20;
        else if (!move.getOriginalInstruction().isCanBlock() && move.getOriginalInstruction().isCanEvade()) defensiveInstruction = 21;
        else defensiveInstruction = ((int)(Math.random() * 2)) + 20;

        Instruction instructionBlock = instructionRepository.findById(defensiveInstruction).get();
        Audio audioBlock = audioRepository.findByInstructionAndLanguage(instructionBlock, language);
        int minExecutionTimeMillis = instructionBlock.getMinExecutionTimeMillis();
        int extraSilenceMillisAfterInstruction = 750;

        if (audioBlock.getLengthMillis() + minExecutionTimeMillis + move.getTotalMoveAudioLengthMillis() + extraSilenceMillisAfterInstruction > roundLengthMillisRemaining) return;

        List<Byte> bytesListAudioMove = new ArrayList<>();

        for (byte value : audioBlock.getAudioFragment()) bytesListAudioMove.add(value);

        int numberOfSilencesToAdd = (int) Math.ceil(minExecutionTimeMillis / (double) silenceLengthMillis);
        for (int i = 0; i < numberOfSilencesToAdd; i++) for (byte value : audioSilence.getAudioFragment()) bytesListAudioMove.add(value);
        bytesListAudioMove.addAll(move.getBytesListAudioMove());

        int numberOfSilencesToAddAfterInstruction = (int) Math.ceil(extraSilenceMillisAfterInstruction / (double) silenceLengthMillis);
        for (int i = 0; i < numberOfSilencesToAddAfterInstruction; i++) for (byte value : audioSilence.getAudioFragment()) bytesListAudioMove.add(value);

        move.setBytesListAudioMove(bytesListAudioMove);
        move.setTotalMoveAudioLengthMillis(move.getTotalMoveAudioLengthMillis() + audioBlock.getLengthMillis() + (numberOfSilencesToAdd * audioSilence.getLengthMillis()) + extraSilenceMillisAfterInstruction);
    }

    private void prependWithInnerRoundBreakIfApplicable(Move move, int roundLengthMillisRemaining) {
        if (Math.random() < 0.9) return;

        int maxInnerRoundBreakMillis = roundLengthMillisRemaining - move.getTotalMoveAudioLengthMillis();
        if (maxInnerRoundBreakMillis > 2000) maxInnerRoundBreakMillis = (int) ((Math.random() * 1500) + 500);
        else maxInnerRoundBreakMillis = (int) (Math.random() * maxInnerRoundBreakMillis);

        List<Byte> bytesListAudioMove = new ArrayList<>();

        int numberOfSilencesToAdd = (int) Math.ceil(maxInnerRoundBreakMillis / (double) silenceLengthMillis);

        for (int i = 0; i < numberOfSilencesToAdd; i++) for (byte value : audioSilence.getAudioFragment()) bytesListAudioMove.add(value);

        bytesListAudioMove.addAll(move.getBytesListAudioMove());
        move.setBytesListAudioMove(bytesListAudioMove);
        move.setTotalMoveAudioLengthMillis(move.getTotalMoveAudioLengthMillis() + (numberOfSilencesToAdd * audioSilence.getLengthMillis()));
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

