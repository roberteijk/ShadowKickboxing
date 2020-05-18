/**
 * Created by Robert van den Eijk on 23-4-2020.
 */

package net.vandeneijk.shadowkickboxing.startup;

import net.vandeneijk.shadowkickboxing.Helper;
import net.vandeneijk.shadowkickboxing.models.*;
import net.vandeneijk.shadowkickboxing.services.*;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Objects;

@Component
public class SeedDatabase {

    private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);

    private final LanguageService languageService;
    private final InstructionService instructionService;
    private final AudioService audioService;
    private final SpeedService speedService;
    private final LengthService lengthService;
    private final FightService fightService;
    private final FightFactory fightFactory;

    @Autowired
    public SeedDatabase(LanguageService languageService, InstructionService instructionService, AudioService audioService, SpeedService speedService, LengthService lengthService, FightService fightService, FightFactory fightFactory) {
        this.languageService = languageService;
        this.instructionService = instructionService;
        this.audioService = audioService;
        this.speedService = speedService;
        this.lengthService = lengthService;
        this.fightService = fightService;
        this.fightFactory = fightFactory;

        seedLanguage();
        seedInstructionAndAudio();
        seedSpeed();
        seedLength();
        seedFight();
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
        languageService.save(new Language(0L, "generic"));
        languageService.save(new Language(1L, "English"));
    }

    private void seedInstructionAndAudio() {
        logger.info("Seeding database with Instruction and Audio.");

        saveInstructionWithAudio(new Instruction("silence", false), new PreAudioMeta("audio/silence.mp3", "generic"));
        saveInstructionWithAudio(new Instruction("block", false), new PreAudioMeta("audio/block.mp3", "English"));
        saveInstructionWithAudio(new Instruction("evade", false), new PreAudioMeta("audio/evade.mp3", "English"));

        saveInstructionWithAudio(new Instruction("10 seconds break", false), new PreAudioMeta("audio/10-seconds-break.mp3", "English"));
        saveInstructionWithAudio(new Instruction("1 minute break", false), new PreAudioMeta("audio/1-minute-break.mp3", "English"));
        saveInstructionWithAudio(new Instruction("break bell", false), new PreAudioMeta("audio/break-bell.mp3", "English"));

        saveInstructionWithAudio(new Instruction("jab", true, true, true, 1.0, 400, 1200), new PreAudioMeta("audio/jab.mp3", "English"));
        saveInstructionWithAudio(new Instruction("double jab", true, true, true, 1.0, 500, 1500), new PreAudioMeta("audio/double-jab.mp3", "English"));
        saveInstructionWithAudio(new Instruction("cross", true, true, true, 0.6, 500, 1500), new PreAudioMeta("audio/cross.mp3", "English"));
        saveInstructionWithAudio(new Instruction("left hook head", true, true, true, 0.4, 600, 1500), new PreAudioMeta("audio/left-hook-head.mp3", "English"));
        saveInstructionWithAudio(new Instruction("right hook head", true, true, true, 0.4, 600, 1500), new PreAudioMeta("audio/right-hook-head.mp3", "English"));
        saveInstructionWithAudio(new Instruction("left uppercut", true, true, true, 0.4, 600, 1500), new PreAudioMeta("audio/left-uppercut.mp3", "English"));
        saveInstructionWithAudio(new Instruction("right uppercut", true, true, true, 0.4, 600, 1500), new PreAudioMeta("audio/right-uppercut.mp3", "English"));
        saveInstructionWithAudio(new Instruction("left hook body", true, true, true, 0.3, 600, 1500), new PreAudioMeta("audio/left-hook-body.mp3", "English"));
        saveInstructionWithAudio(new Instruction("right hook body", true, true, true, 0.3, 600, 1500), new PreAudioMeta("audio/right-hook-body.mp3", "English"));
        saveInstructionWithAudio(new Instruction("left low kick", true, true, true, 0.3, 800, 1800), new PreAudioMeta("audio/left-low-kick.mp3", "English"));
        saveInstructionWithAudio(new Instruction("right low kick", true, true, true, 0.3, 800, 1800), new PreAudioMeta("audio/right-low-kick.mp3", "English"));
        saveInstructionWithAudio(new Instruction("clinch left knee", true, false, false, 0.25, 1500, 2500), new PreAudioMeta("audio/clinch-left-knee.mp3", "English"));
        saveInstructionWithAudio(new Instruction("clinch right knee", true, false, false, 0.25, 1500, 2500), new PreAudioMeta("audio/clinch-right-knee.mp3", "English"));
        saveInstructionWithAudio(new Instruction("left front kick", true, true, true, 0.25, 700, 1500), new PreAudioMeta("audio/left-front-kick.mp3", "English"));
        saveInstructionWithAudio(new Instruction("right front kick", true, true, true, 0.25, 700, 1500), new PreAudioMeta("audio/right-front-kick.mp3", "English"));
        saveInstructionWithAudio(new Instruction("left middle kick", true, true, false, 0.15, 900, 1800), new PreAudioMeta("audio/left-middle-kick.mp3", "English"));
        saveInstructionWithAudio(new Instruction("right middle kick", true, true, false, 0.15, 900, 1800), new PreAudioMeta("audio/right-middle-kick.mp3", "English"));
        saveInstructionWithAudio(new Instruction("left high kick", true, true, true, 0.1, 900, 2000), new PreAudioMeta("audio/left-high-kick.mp3", "English"));
        saveInstructionWithAudio(new Instruction("right high kick", true, true, true, 0.1, 900, 2000), new PreAudioMeta("audio/right-high-kick.mp3", "English"));
    }

    private void saveInstructionWithAudio(Instruction instruction, PreAudioMeta... preAudioMetaArray) {
        if (instructionService.findByDescription(instruction.getDescription()).isPresent()) return;
        else instructionService.save(instruction);

        for (PreAudioMeta preAudioMeta : preAudioMetaArray) {
            File file;
            if ((file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(preAudioMeta.getFileLocation())).getFile())).length() > 0)
                languageService.findByDescription(preAudioMeta.getLanguageDescription()).ifPresent(x -> audioService.save(new Audio(instruction, x, Helper.getAudioFileLengthMillis(file), readFileToByteArray(file))));
        }
    }

    private void seedSpeed() {
        logger.info("Seeding database with Speed.");

        speedService.save(new Speed(0L, "Extra Slow", 2.25));
        speedService.save(new Speed(1L, "Slow", 1.50));
        speedService.save(new Speed(2L, "Normal", 1.0));
        speedService.save(new Speed(3L, "Fast", 0.66));
        speedService.save(new Speed(4L, "Extra Fast", 0.4356));
    }

    private void seedLength() {
        logger.info("Seeding database with Length.");

        lengthService.save(new Length(0L, "Practice (1 round)", 1));
        lengthService.save(new Length(1L, "Full Fight (3 rounds)", 3));
        lengthService.save(new Length(2L, "Championship Fight (5 rounds)", 5));
    }

    private void seedFight() {
        for (Speed speed : speedService.findAll()) {
            for (Length length : lengthService.findAll()) {
                long numberOfFightsByCriteria = fightService.countBySpeedAndLength(speed, length);
                for (long i = numberOfFightsByCriteria; i < 2; i++) {
                    fightFactory.createFight(1, speed, length);
                }
            }
        }
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
        }
        return new byte[0];
    }
}
