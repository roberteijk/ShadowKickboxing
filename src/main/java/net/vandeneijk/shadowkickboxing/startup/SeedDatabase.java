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

    private void seedLanguage() {
        logger.info("Seeding database with Language.");
        languageService.save(new Language(0L, "Generic"));
        languageService.save(new Language(1L, "English"));
    }

    private void seedInstructionAndAudio() {
        logger.info("Seeding database with Instruction and Audio.");

        instructionService.save(new Instruction(0L, "silence", false));
        File file = new File(getClass().getClassLoader().getResource("audio/silence.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(0L).get(), languageService.findById(0L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(20L, "block", false));
        file = new File(getClass().getClassLoader().getResource("audio/block.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(20L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(21L, "evade", false));
        file = new File(getClass().getClassLoader().getResource("audio/evade.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(21L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(40L, "10 seconds break", false));
        file = new File(getClass().getClassLoader().getResource("audio/10-seconds-break.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(40L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(41L, "1 minute break", false));
        file = new File(getClass().getClassLoader().getResource("audio/1-minute-break.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(41L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(42L, "break bell", false));
        file = new File(getClass().getClassLoader().getResource("audio/break-bell.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(42L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(100L, "jab",  true, true, true, 1.0, 400, 1200));
        file = new File(getClass().getClassLoader().getResource("audio/jab.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(100L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(101L, "double jab", true, true, true, 1.0, 500, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/double-jab.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(101L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(102L, "cross", true, true, true, 0.6, 500, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/cross.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(102L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(103L, "left hook head", true, true, true, 0.4, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/left-hook-head.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(103L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(104L, "right hook head", true, true, true, 0.4, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/right-hook-head.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(104L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(105L, "left uppercut", true, true, true, 0.4, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/left-uppercut.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(105L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(106L, "right uppercut", true, true, true, 0.4, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/right-uppercut.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(106L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(107L, "left hook body", true, true, true, 0.3, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/left-hook-body.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(107L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(108L, "right hook body", true, true, true, 0.3, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/right-hook-body.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(108L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(109L, "left low kick", true, true, true, 0.3, 800, 1800));
        file = new File(getClass().getClassLoader().getResource("audio/left-low-kick.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(109L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(110L, "right low kick", true, true, true, 0.3, 800, 1800));
        file = new File(getClass().getClassLoader().getResource("audio/right-low-kick.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(110L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(111L, "clinch left knee", true, false, false, 0.25, 1500, 2500));
        file = new File(getClass().getClassLoader().getResource("audio/clinch-left-knee.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(111L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(112L, "clinch right knee", true, false, false, 0.25, 1500, 2500));
        file = new File(getClass().getClassLoader().getResource("audio/clinch-right-knee.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(112L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(113L, "left front kick", true, true, true, 0.25, 700, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/left-front-kick.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(113L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(114L, "right front kick", true, true, true, 0.25, 700, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/right-front-kick.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(114L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(115L, "left middle kick", true, true, false, 0.15, 900, 1800));
        file = new File(getClass().getClassLoader().getResource("audio/left-middle-kick.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(115L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(116L, "right middle kick", true, true, false, 0.15, 900, 1800));
        file = new File(getClass().getClassLoader().getResource("audio/right-middle-kick.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(116L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(117L, "left high kick", true, true, true, 0.10, 900, 2000));
        file = new File(getClass().getClassLoader().getResource("audio/left-high-kick.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(117L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionService.save(new Instruction(118L, "right high kick", true, true, true, 0.10, 900, 2000));
        file = new File(getClass().getClassLoader().getResource("audio/right-high-kick.mp3").getFile());
        audioService.save(new Audio(instructionService.findById(118L).get(), languageService.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

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
