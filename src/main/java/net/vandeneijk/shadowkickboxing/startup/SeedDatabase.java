/**
 * Created by Robert van den Eijk on 23-4-2020.
 */

package net.vandeneijk.shadowkickboxing.startup;

import net.vandeneijk.shadowkickboxing.models.*;
import net.vandeneijk.shadowkickboxing.services.*;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightFactory;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightFactoryJob;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

@Component
public class SeedDatabase {

    private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);

    private final LanguageService languageService;
    private final InstructionService instructionService;
    private final AudioService audioService;
    private final SpeedService speedService;
    private final LengthService lengthService;
    private final DefensiveModeService defensiveModeService;
    private final ExpertiseService expertiseService;
    private final FightService fightService;
    private final FightFactory fightFactory;

    @Autowired
    public SeedDatabase(LanguageService languageService, InstructionService instructionService, AudioService audioService, SpeedService speedService, LengthService lengthService, DefensiveModeService defensiveModeService, ExpertiseService expertiseService, FightService fightService, FightFactory fightFactory) {
        this.languageService = languageService;
        this.instructionService = instructionService;
        this.audioService = audioService;
        this.speedService = speedService;
        this.lengthService = lengthService;
        this.defensiveModeService = defensiveModeService;
        this.expertiseService = expertiseService;
        this.fightService = fightService;
        this.fightFactory = fightFactory;

        seedLanguage();
        seedExpertise();
        seedDefensiveMode();
        seedInstructionAndAudio();
        seedSpeed();
        seedLength();
        seedFight();
    }
    
    private static class PreInstructionMeta {
        private final String instructionDescription;
        private final String[] expertiseDescriptionsIn2Chars;
        private final Double callFrequencyWeight;
        private final Integer minExecutionTimeMillis;
        private final Integer maxExecutionTimeMillis;

        private PreInstructionMeta(String instructionDescription, String[] expertiseDescriptionsIn2Chars, Double callFrequencyWeight, Integer minExecutionTimeMillis, Integer maxExecutionTimeMillis) {
            this.instructionDescription = instructionDescription;
            this.expertiseDescriptionsIn2Chars = expertiseDescriptionsIn2Chars;
            this.callFrequencyWeight = callFrequencyWeight;
            this.minExecutionTimeMillis = minExecutionTimeMillis;
            this.maxExecutionTimeMillis = maxExecutionTimeMillis;
        }

        private String getInstructionDescription() {
            return instructionDescription;
        }

        private String[] getExpertiseDescriptionsIn2Chars() {
            return expertiseDescriptionsIn2Chars;
        }

        private Double getCallFrequencyWeight() {
            return callFrequencyWeight;
        }

        private Integer getMinExecutionTimeMillis() {
            return minExecutionTimeMillis;
        }

        private Integer getMaxExecutionTimeMillis() {
            return maxExecutionTimeMillis;
        }
    }

    private static class PreAudioMeta {
        private final String fileLocation;
        private final String languageDescription;

        private PreAudioMeta(String fileLocation, String languageDescription) {
            this.fileLocation = fileLocation;
            this.languageDescription = languageDescription;
        }

        private String getFileLocation() {
            return fileLocation;
        }

        private String getLanguageDescription() {
            return languageDescription;
        }
    }

    private void seedLanguage() {
        logger.info("Seeding database with Language.");
        languageService.saveIfDescriptionUnique(new Language("generic"));
        languageService.saveIfDescriptionUnique(new Language("English"));
    }

    private void seedExpertise() {
        logger.info("Seeding database with Expertise.");

        expertiseService.save(new Expertise(0L, "Shadow Kickboxing", "fb", true, true));
        expertiseService.save(new Expertise(1L, "Shadow Kickboxing with Elbows", "fe", true, true));
        expertiseService.save(new Expertise(2L, "Shadow Boxing (upper body only)", "ub", true, false));
    }

    private void seedDefensiveMode() {
        logger.info("Seeding database with DefensiveMode.");

        defensiveModeService.save(new DefensiveMode(0L,"Block & Evade", "be", true, true));
        defensiveModeService.save(new DefensiveMode(1L,"Block Only", "bo", true, false));
        defensiveModeService.save(new DefensiveMode(2L,"Evade Only", "eo", false, true));
        defensiveModeService.save(new DefensiveMode(3L,"None (no defense)", "no", false, false));
    }

    private void seedInstructionAndAudio() {
        logger.info("Seeding database with Instruction and Audio.");

        saveInstructionWithAudio(new Instruction("silence", false), false, false, new PreAudioMeta("audio/silence.mp3", "generic"));
        saveInstructionWithAudio(new Instruction("block", false), false, false, new PreAudioMeta("audio/block.mp3", "English"));
        saveInstructionWithAudio(new Instruction("evade", false), false, false, new PreAudioMeta("audio/evade.mp3", "English"));

        saveInstructionWithAudio(new Instruction("10 seconds break", false), false, false, new PreAudioMeta("audio/10-seconds-break.mp3", "English"));
        saveInstructionWithAudio(new Instruction("1 minute break", false), false, false, new PreAudioMeta("audio/1-minute-break.mp3", "English"));
        saveInstructionWithAudio(new Instruction("break bell end of fight", false), false, false, new PreAudioMeta("audio/break-bell-end-fight.mp3", "English"));

        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("jab", new String[]{"fb", "fe", "ub"}, 1.0, 500, 1000)), true, true, new PreAudioMeta("audio/jab.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("cross", new String[]{"fb", "fe", "ub"}, 0.9, 500, 1000)), true, true, new PreAudioMeta("audio/cross.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("double jab", new String[]{"fb", "fe", "ub"}, 1.0, 600, 1100)), true, true, new PreAudioMeta("audio/double-jab.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("jab cross", new String[]{"fb", "fe", "ub"}, 0.5, 750, 1250)), true, true, new PreAudioMeta("audio/jab-cross.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("double cross", new String[]{"fb", "fe", "ub"}, 0.5, 800, 1300)), true, true, new PreAudioMeta("audio/double-cross.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left hook head", new String[]{"fb", "fe", "ub"}, 0.5, 600, 1100)), true, true, new PreAudioMeta("audio/left-hook-head.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right hook head", new String[]{"fb", "fe", "ub"}, 0.5, 600, 1100)), true, true, new PreAudioMeta("audio/right-hook-head.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left uppercut", new String[]{"fb", "fe", "ub"}, 0.4, 600, 1100)), true, true, new PreAudioMeta("audio/left-uppercut.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right uppercut", new String[]{"fb", "fe", "ub"}, 0.4, 600, 1100)), true, true, new PreAudioMeta("audio/right-uppercut.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left hook body", new String[]{"fb", "fe", "ub"}, 0.4, 600, 1200)), true, true, new PreAudioMeta("audio/left-hook-body.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right hook body", new String[]{"fb", "fe", "ub"}, 0.25, 600, 1200)), true, true, new PreAudioMeta("audio/right-hook-body.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("double jab cross", new String[]{"fb", "fe", "ub"}, 0.25, 850, 1350)), true, true, new PreAudioMeta("audio/double-jab-cross.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left low kick", new String[]{"fb", "fe"}, 0.3, 900, 1500)),true, true, new PreAudioMeta("audio/left-low-kick.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right low kick", new String[]{"fb", "fe"}, 0.3, 900, 1500)), true, true, new PreAudioMeta("audio/right-low-kick.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("clinch left knee", new String[]{"fb", "fe"}, 0.25, 1500, 2400)), false, false, new PreAudioMeta("audio/clinch-left-knee.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("clinch right knee", new String[]{"fb", "fe"}, 0.25, 1500, 2400)), false, false, new PreAudioMeta("audio/clinch-right-knee.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left front kick", new String[]{"fb", "fe"}, 0.4, 900, 1400)), true, true, new PreAudioMeta("audio/left-front-kick.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right front kick", new String[]{"fb", "fe"}, 0.25, 900, 1400)), true, true, new PreAudioMeta("audio/right-front-kick.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left middle kick", new String[]{"fb", "fe"}, 0.15, 1100, 1800)), true, false, new PreAudioMeta("audio/left-middle-kick.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right middle kick", new String[]{"fb", "fe"}, 0.15, 900, 1800)), true, false, new PreAudioMeta("audio/right-middle-kick.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left high kick", new String[]{"fb", "fe"}, 0.1, 1100, 1800)), true, true, new PreAudioMeta("audio/left-high-kick.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right high kick", new String[]{"fb", "fe"}, 0.1, 900, 1800)), true, true, new PreAudioMeta("audio/right-high-kick.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left elbow downward", new String[]{"fe"}, 0.1, 600, 1100)), false, true, new PreAudioMeta("audio/left-elbow-downward.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right elbow downward", new String[]{"fe"}, 0.1, 600, 1100)), false, true, new PreAudioMeta("audio/right-elbow-downward.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left elbow sideways", new String[]{"fe"}, 0.1, 600, 1100)), true, true, new PreAudioMeta("audio/left-elbow-sideways.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right elbow sideways", new String[]{"fe"}, 0.1, 600, 1100)), true, true, new PreAudioMeta("audio/right-elbow-sideways.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("left elbow upward", new String[]{"fe"}, 0.1, 600, 1100)), false, true, new PreAudioMeta("audio/left-elbow-upward.mp3", "English"));
        saveInstructionWithAudio(buildMoveInstruction(new PreInstructionMeta("right elbow upward", new String[]{"fe"}, 0.1, 600, 1100)), false, true, new PreAudioMeta("audio/right-elbow-upward.mp3", "English"));
    }

    private Instruction buildMoveInstruction(PreInstructionMeta preInstructionMeta) {
        Instruction instruction = new Instruction(preInstructionMeta.getInstructionDescription(), true, preInstructionMeta.getCallFrequencyWeight(), preInstructionMeta.getMinExecutionTimeMillis(), preInstructionMeta.getMaxExecutionTimeMillis());
        addExpertiseToInstruction(instruction, preInstructionMeta.getExpertiseDescriptionsIn2Chars());
        addDefensiveModeToInstruction(instruction);

        return instruction;
    }

    private void addExpertiseToInstruction(Instruction instruction, String[] expertiseDescriptionsIn2Chars) {
        for (String expertiseDescriptionIn2Chars : expertiseDescriptionsIn2Chars) instruction.addExpertiseToCollection(expertiseService.findByDescriptionIn2Chars(expertiseDescriptionIn2Chars));
    }

    private void addDefensiveModeToInstruction(Instruction instruction, String[] defensiveModeDescriptionsIn2Chars) {
        for (String defensiveModeDescriptionIn2Chars : defensiveModeDescriptionsIn2Chars) instruction.addDefensiveModeToCCollection(defensiveModeService.findByDescriptionIn2Chars(defensiveModeDescriptionIn2Chars));
    }

    private void addDefensiveModeToInstruction(Instruction instruction) {
        defensiveModeService.findAll().forEach(instruction::addDefensiveModeToCCollection);
    }

    private void saveInstructionWithAudio(Instruction instruction, boolean saveBlockVariant, boolean saveEvadeVariant, PreAudioMeta... preAudioMetaArray) {
        if (!instructionService.saveIfDescriptionUnique(instruction)) return;

        for (PreAudioMeta preAudioMeta : preAudioMetaArray) {

            try (BufferedInputStream in = new BufferedInputStream(getClass().getResourceAsStream("/" + preAudioMeta.fileLocation))) {
                File file = getFileFromStream(in);
                if (file != null) languageService.findByDescription(preAudioMeta.getLanguageDescription()).ifPresent(x -> audioService.save(new Audio(instruction, x, getAudioFileLengthMillis(file), readFileToByteArray(file))));
            } catch (IOException ioEx) {
                logger.warn("Could not read of find file: " + preAudioMeta.getFileLocation());
                ioEx.printStackTrace();
            }
        }

        if (saveBlockVariant) saveDefensiveInstructionVariants(instruction, "block",  new String[]{"be", "bo"});
        if (saveEvadeVariant) saveDefensiveInstructionVariants(instruction, "evade", new String[]{"be", "eo"});
    }

    private void saveDefensiveInstructionVariants(Instruction originalInstruction, String instructionDescriptionToProcess, String[] defensiveModeDescriptionsIn2Chars) {
        Instruction defensiveInstruction = new Instruction(instructionDescriptionToProcess + " " + originalInstruction.getDescription(), originalInstruction.isMove(), originalInstruction.getCallFrequencyWeight() * 0.1, originalInstruction.getMinExecutionTimeMillis() + 400, originalInstruction.getMaxExecutionTimeMillis() + 400);
        defensiveInstruction.setExpertiseSet(new HashSet<Expertise>(originalInstruction.getExpertiseSet()));
        addDefensiveModeToInstruction(defensiveInstruction, defensiveModeDescriptionsIn2Chars);
        if (!instructionService.saveIfDescriptionUnique(defensiveInstruction)) return;

        for (Audio audio : audioService.findByInstruction(originalInstruction)) {
            Audio audioToPrepend = audioService.findByInstructionAndLanguage(instructionService.findByDescription(instructionDescriptionToProcess).get(), audio.getLanguage());
            byte[] audioFragmentToPrepend = audioToPrepend.getAudioFragment();
            byte[] audioFragmentOriginalInstruction = audio.getAudioFragment();
            byte[] bothAudioFragments = ArrayUtils.addAll(audioFragmentToPrepend, audioFragmentOriginalInstruction);
            Integer bothAudioFragmentMillis = audioToPrepend.getLengthMillis() + audio.getLengthMillis();

            audioService.save(new Audio(defensiveInstruction, audio.getLanguage(), bothAudioFragmentMillis, bothAudioFragments));
        }
    }


    private void seedSpeed() {
        logger.info("Seeding database with Speed.");

        speedService.save(new Speed(0L, "Extra Slow", "48", 2.25));
        speedService.save(new Speed(1L, "Slow", "49", 1.50));
        speedService.save(new Speed(2L, "Normal", "50", 1.0));
        speedService.save(new Speed(3L, "Fast", "51", 0.66));
        speedService.save(new Speed(4L, "Extra Fast", "52", 0.4356));
    }

    private void seedLength() {
        logger.info("Seeding database with Length.");

        lengthService.save(new Length(0L, "Practice (1 round)", "01", 1));
        lengthService.save(new Length(1L, "Full Fight (3 rounds)", "03", 3));
        lengthService.save(new Length(2L, "Championship Fight (5 rounds)", "05", 5));
    }

    private void seedFight() {
        Map<Integer, Runnable> fightsToCreate = new TreeMap<>();
        int count = 0;

        for (int i = 1; i <= 1; i++) {
            for (Speed speed : speedService.findAll()) {
                for (Length length : lengthService.findAll()) {
                    for (DefensiveMode defensiveMode : defensiveModeService.findAll()) {
                        for (Expertise expertise : expertiseService.findAll()) {
                            long numberOfFightsByCriteria = fightService.countBySpeedAndLengthAndDefensiveModeAndExpertiseAndZdtFirstDownload(speed, length, defensiveMode, expertise, null);
                            if (numberOfFightsByCriteria < i) fightsToCreate.put(count++, () -> {fightFactory.createFight(new FightFactoryJob("English", instructionService.findAll(), expertise, defensiveMode, speed, length));});
                        }
                    }
                }
            }
        }

        fightsToCreate.forEach((k, v) -> {v.run();});
    }

    private File getFileFromStream(InputStream in) {
        try {
            File tempFile = File.createTempFile("stream2fil", ".tmp");
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(in, out);
            }
            return tempFile;
        } catch (IOException ioEx) {
            logger.warn("Could not create a file from a stream. Exception: " + ioEx);
            ioEx.printStackTrace();
        }
        return null;
    }

    private int getAudioFileLengthMillis(File file) {
        try {
            AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(file);
            Map<String, Object> properties = baseFileFormat.properties();
            return ((Long) properties.get("duration")).intValue() / 1000;
        } catch (UnsupportedAudioFileException | IOException miscEx) {
            logger.error("Error determining the length of an audio file for seeding database. Exception: " + miscEx);
            miscEx.printStackTrace();
        }
        return 0;
    }

    private byte[] readFileToByteArray(File file) {
        try (InputStream is = new BufferedInputStream(new FileInputStream(file)); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) > 0) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        } catch (IOException ioEx) {
            logger.error("Error reading audio file to byte[] for seeding database. Exception: " + ioEx);
            ioEx.printStackTrace();
        }
        return new byte[0];
    }
}
