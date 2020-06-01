/**
 * Created by Robert van den Eijk on 6-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services.fightfactoryservice;

import net.vandeneijk.shadowkickboxing.models.*;
import net.vandeneijk.shadowkickboxing.services.AudioService;
import net.vandeneijk.shadowkickboxing.services.FightService;
import net.vandeneijk.shadowkickboxing.services.InstructionService;
import net.vandeneijk.shadowkickboxing.services.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@DependsOn({"taskExecutor"})
public class FightFactory {

    private static final Logger logger = LoggerFactory.getLogger(FightFactory.class);

    private final InstructionService instructionService;
    private final LanguageService languageService;
    private final AudioService audioService;
    private final FightService fightService;

    private List<Long> instructionCallWeightDistribution;
    private Audio audioSilence;
    private int silenceLengthMillis;


    public FightFactory(InstructionService instructionService, LanguageService languageService, AudioService audioService, FightService fightService) {
        this.instructionService = instructionService;
        this.languageService = languageService;
        this.audioService = audioService;
        this.fightService = fightService;
    }

    @Async
    public void createFight(String languageDescription, Speed speed, Length length, DefensiveMode defensiveMode, BodyHalf bodyHalf) {
        seedInstructionCallWeightDistribution();
        getAudioSilence();

        int roundLengthSeconds = 179;
        Language language = languageService.findByDescription(languageDescription).get();
        List<List<Byte>> rounds = new ArrayList<>();

        while (rounds.size() < length.getNumberRounds()) {
            rounds.add(createRound(roundLengthSeconds, language, speed, defensiveMode, bodyHalf));
        }

        List<Byte> fight = new ArrayList<>();
        addCountdownBeforeStartFight(fight, language);
        addBreaksBetweenRounds(rounds, fight, language);
        addBreakBellAfterFight(fight, language);

        writeFightToDatabase(fight, language, speed, length, defensiveMode, bodyHalf);
    }


    private List<Byte> createRound(int roundLengthSeconds, Language language, Speed speed, DefensiveMode defensiveMode, BodyHalf bodyHalf) {
        List<Byte> round = new ArrayList<>();
        int roundLengthMillisRemaining = roundLengthSeconds * 1000;
        while (true) {
            Move move = createMove(language, speed, roundLengthMillisRemaining, bodyHalf);
            if (move == null) break;

            prependWithBlockIfApplicable(move, language, speed, defensiveMode, roundLengthMillisRemaining);
            prependWithInnerRoundBreakIfApplicable(move, speed, roundLengthMillisRemaining);

            round.addAll(move.getBytesListAudioMove());
            roundLengthMillisRemaining -= move.getTotalMoveAudioLengthMillis();
        }
        return round;
    }

    private Move createMove(Language language, Speed speed, int roundLengthMillisRemaining, BodyHalf bodyHalf) {
        Instruction instructionMove;
        do {
            long moveToAdd = instructionCallWeightDistribution.get((int) (Math.random() * instructionCallWeightDistribution.size()));
            instructionMove = instructionService.findById(moveToAdd).get();
        } while ((instructionMove.isUseUpperBody() && !bodyHalf.isAllowUpperBody()) || (instructionMove.isUseLowerBody() && !bodyHalf.isAllowLowerBody()));

        Audio audioMove = audioService.findByInstructionAndLanguage(instructionMove, language);
        int minExecutionTimeMillis = instructionMove.getMinExecutionTimeMillis();
        int maxExecutionTimeMillis = instructionMove.getMaxExecutionTimeMillis();

        if (audioMove.getLengthMillis() + (minExecutionTimeMillis * speed.getExecutionMillisMultiplier()) > roundLengthMillisRemaining) return null;

        List<Byte> bytesListAudioMove = new ArrayList<>();
        int oldRoundLengthMillisRemaining = roundLengthMillisRemaining;
        for (byte value : audioMove.getAudioFragment()) bytesListAudioMove.add(value);
        roundLengthMillisRemaining -= audioMove.getLengthMillis();

        int executionTimeMillis = (int) (((Math.random() * (maxExecutionTimeMillis - minExecutionTimeMillis)) + minExecutionTimeMillis) * speed.getExecutionMillisMultiplier());
        if (executionTimeMillis > roundLengthMillisRemaining) executionTimeMillis = roundLengthMillisRemaining;
        int numberOfSilencesToAdd = (int) Math.ceil(executionTimeMillis / (double) silenceLengthMillis);

        for (int i = 0; i < numberOfSilencesToAdd; i++) for (byte value : audioSilence.getAudioFragment()) bytesListAudioMove.add(value);
        roundLengthMillisRemaining -= numberOfSilencesToAdd * silenceLengthMillis;

        return new Move(bytesListAudioMove, oldRoundLengthMillisRemaining - roundLengthMillisRemaining, instructionMove);
    }

    private void prependWithBlockIfApplicable(Move move, Language language, Speed speed, DefensiveMode defensiveMode, int roundLengthMillisRemaining) {
        if ((!defensiveMode.isAllowBlock() && !defensiveMode.isAllowEvade()) || (!move.getOriginalInstruction().isCanBlock() && !move.getOriginalInstruction().isCanEvade())) return;
        else if (Math.random() < 0.80) return;

        String defensiveInstruction = null;
        List<String> defensiveInstructionOptionsAvailable = new ArrayList<>();
        if (move.getOriginalInstruction().isCanBlock() && defensiveMode.isAllowBlock()) defensiveInstructionOptionsAvailable.add("block");
        if (move.getOriginalInstruction().isCanEvade() && defensiveMode.isAllowEvade()) defensiveInstructionOptionsAvailable.add("evade");
        if (defensiveInstructionOptionsAvailable.size() > 0) defensiveInstruction = defensiveInstructionOptionsAvailable.get((int) (Math.random() * defensiveInstructionOptionsAvailable.size()));
        else return;

        Instruction instructionBlock = instructionService.findByDescription(defensiveInstruction).get();
        Audio audioBlock = audioService.findByInstructionAndLanguage(instructionBlock, language);
        int minExecutionTimeMillis = instructionBlock.getMinExecutionTimeMillis();
        int extraSilenceMillisAfterInstruction = (int) (400 * speed.getExecutionMillisMultiplier());

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

    private void prependWithInnerRoundBreakIfApplicable(Move move, Speed speed, int roundLengthMillisRemaining) {
        if (Math.random() < 0.9) return;

        int maxInnerRoundBreakMillis = roundLengthMillisRemaining - move.getTotalMoveAudioLengthMillis();
        if (maxInnerRoundBreakMillis > 2000 * speed.getExecutionMillisMultiplier()) maxInnerRoundBreakMillis = (int) (((Math.random() * 1500) + 500) * speed.getExecutionMillisMultiplier());
        else maxInnerRoundBreakMillis = (int) (Math.random() * maxInnerRoundBreakMillis);

        List<Byte> bytesListAudioMove = new ArrayList<>();

        int numberOfSilencesToAdd = (int) Math.ceil(maxInnerRoundBreakMillis / (double) silenceLengthMillis);

        for (int i = 0; i < numberOfSilencesToAdd; i++) for (byte value : audioSilence.getAudioFragment()) bytesListAudioMove.add(value);

        bytesListAudioMove.addAll(move.getBytesListAudioMove());
        move.setBytesListAudioMove(bytesListAudioMove);
        move.setTotalMoveAudioLengthMillis(move.getTotalMoveAudioLengthMillis() + (numberOfSilencesToAdd * audioSilence.getLengthMillis()));
    }

    private void addCountdownBeforeStartFight(List<Byte> fight, Language language) {
        Instruction instruction10SecondsBreak = instructionService.findByDescription("10 seconds break").get();
        Audio audio10SecondsBreak = audioService.findByInstructionAndLanguage(instruction10SecondsBreak, language);

        for (byte value : audio10SecondsBreak.getAudioFragment()) fight.add(value);
    }

    private void addBreaksBetweenRounds(List<List<Byte>> rounds, List<Byte> fight, Language language) {
        Instruction instruction1MinuteBreak = instructionService.findByDescription("1 minute break").get();
        Audio audio1MinuteBreak = audioService.findByInstructionAndLanguage(instruction1MinuteBreak, language);

        for (int i = 0; i < rounds.size(); i++) {
            for (byte value : rounds.get(i)) fight.add(value);
            if (i < rounds.size() - 1) for (byte value : audio1MinuteBreak.getAudioFragment()) fight.add(value);
        }
    }

    private void addBreakBellAfterFight(List<Byte> fight, Language language) {
        Instruction instructionBreakBell = instructionService.findByDescription("break bell end of fight").get();
        Audio audioBreakBell = audioService.findByInstructionAndLanguage(instructionBreakBell, language);

        for (byte value : audioBreakBell.getAudioFragment()) fight.add(value);
    }

    private synchronized void writeFightToDatabase(List<Byte> fight, Language language, Speed speed, Length length, DefensiveMode defensiveMode, BodyHalf bodyHalf) {
        byte[] fightByteArray = new byte[fight.size()];
        for (int i = 0; i < fightByteArray.length; i++) fightByteArray[i] = fight.get(i);

        FightAudioData fightAudioData = new FightAudioData(fightByteArray);
        fightService.save(new Fight(getRandomId(), language, speed, length, defensiveMode, bodyHalf), fightAudioData);

        logger.info("Fight created and stored in database: " + speed.getDescriptionIn2Chars() + length.getDescriptionIn2Chars() + defensiveMode.getDescriptionIn2Chars() + bodyHalf.getDescriptionIn2Chars() + "   Size: " + fight.size());
    }

    private synchronized void seedInstructionCallWeightDistribution() {
        if (instructionCallWeightDistribution != null) return;
        instructionCallWeightDistribution = new ArrayList<>();
        for (Instruction instruction : instructionService.findAll()) {
            if (!instruction.isMove()) continue;
            for (int i = 0; i < instruction.getCallFrequencyWeight() * 100; i++) {
                instructionCallWeightDistribution.add(instruction.getInstructionId());
            }
        }
    }

    private synchronized void getAudioSilence() {
        if (audioSilence != null) return;
        Instruction instruction = instructionService.findByDescription("silence").get();
        Language language = languageService.findByDescription("generic").get();
        audioSilence = audioService.findByInstructionAndLanguage(instruction, language);
        silenceLengthMillis = audioSilence.getLengthMillis();
    }

    private String getRandomId() {
        char[] allowedChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        StringBuilder randomId = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            randomId.append(allowedChars[((int) (Math.random() * allowedChars.length))]);
        }

        if (fightService.existsByRandomId(randomId.toString())) return getRandomId();
        return randomId.toString();
    }
}

