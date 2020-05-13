/**
 * Created by Robert van den Eijk on 23-4-2020.
 */

package net.vandeneijk.shadowkickboxing.startup;

import net.vandeneijk.shadowkickboxing.Helper;
import net.vandeneijk.shadowkickboxing.fightfactory.FightFactory;
import net.vandeneijk.shadowkickboxing.models.Audio;
import net.vandeneijk.shadowkickboxing.models.Instruction;
import net.vandeneijk.shadowkickboxing.models.Language;
import net.vandeneijk.shadowkickboxing.models.Speed;
import net.vandeneijk.shadowkickboxing.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class SeedDatabase {

    private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);

    private final LanguageRepository languageRepository;
    private final InstructionRepository instructionRepository;
    private final AudioRepository audioRepository;
    private final SpeedRepository speedRepository;
    private final FightRepository fightRepository;
    private final FightFactory fightFactory;

    @Autowired
    public SeedDatabase(LanguageRepository languageRepository, InstructionRepository instructionRepository, AudioRepository audioRepository, SpeedRepository speedRepository, FightRepository fightRepository, FightFactory fightFactory) {
        this.languageRepository = languageRepository;
        this.instructionRepository = instructionRepository;
        this.audioRepository = audioRepository;
        this.speedRepository = speedRepository;
        this.fightRepository = fightRepository;
        this.fightFactory = fightFactory;

        seedLanguage();
        seedInstructionAndAudio();
        seedSpeedOptions();
        seedFight();
    }

    private void seedLanguage() {
        logger.info("Seeding database with Language.");
        languageRepository.save(new Language(0L, "Generic"));
        languageRepository.save(new Language(1L, "English"));
    }

    private void seedInstructionAndAudio() {
        logger.info("Seeding database with Instruction and Audio.");

        instructionRepository.save(new Instruction(0L, "silence", false));
        File file = new File(getClass().getClassLoader().getResource("audio/silence.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(0L).get(), languageRepository.findById(0L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(20L, "block", false));
        file = new File(getClass().getClassLoader().getResource("audio/block.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(20L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(21L, "evade", false));
        file = new File(getClass().getClassLoader().getResource("audio/evade.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(21L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(40L, "10 seconds break", false));
        file = new File(getClass().getClassLoader().getResource("audio/10-seconds-break.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(40L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(41L, "1 minute break", false));
        file = new File(getClass().getClassLoader().getResource("audio/1-minute-break.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(41L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(42L, "break bell", false));
        file = new File(getClass().getClassLoader().getResource("audio/break-bell.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(42L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(100L, "jab",  true, true, true, 1.0, 400, 1200));
        file = new File(getClass().getClassLoader().getResource("audio/jab.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(100L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(101L, "double jab", true, true, true, 1.0, 500, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/double-jab.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(101L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(102L, "cross", true, true, true, 0.6, 500, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/cross.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(102L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(103L, "left hook head", true, true, true, 0.4, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/left-hook-head.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(103L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(104L, "right hook head", true, true, true, 0.4, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/right-hook-head.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(104L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(105L, "left uppercut", true, true, true, 0.4, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/left-uppercut.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(105L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(106L, "right uppercut", true, true, true, 0.4, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/right-uppercut.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(106L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(107L, "left hook body", true, true, true, 0.3, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/left-hook-body.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(107L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(108L, "right hook body", true, true, true, 0.3, 600, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/right-hook-body.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(108L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(109L, "left low kick", true, true, true, 0.3, 800, 1800));
        file = new File(getClass().getClassLoader().getResource("audio/left-low-kick.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(109L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(110L, "right low kick", true, true, true, 0.3, 800, 1800));
        file = new File(getClass().getClassLoader().getResource("audio/right-low-kick.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(110L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(111L, "clinch left knee", true, false, false, 0.25, 1500, 2500));
        file = new File(getClass().getClassLoader().getResource("audio/clinch-left-knee.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(111L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(112L, "clinch right knee", true, false, false, 0.25, 1500, 2500));
        file = new File(getClass().getClassLoader().getResource("audio/clinch-right-knee.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(112L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(113L, "left front kick", true, true, true, 0.25, 700, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/left-front-kick.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(113L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(114L, "right front kick", true, true, true, 0.25, 700, 1500));
        file = new File(getClass().getClassLoader().getResource("audio/right-front-kick.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(114L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(115L, "left middle kick", true, true, false, 0.15, 900, 1800));
        file = new File(getClass().getClassLoader().getResource("audio/left-middle-kick.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(115L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(116L, "right middle kick", true, true, false, 0.15, 900, 1800));
        file = new File(getClass().getClassLoader().getResource("audio/right-middle-kick.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(116L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(117L, "left high kick", true, true, true, 0.10, 900, 2000));
        file = new File(getClass().getClassLoader().getResource("audio/left-high-kick.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(117L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

        instructionRepository.save(new Instruction(118L, "right high kick", true, true, true, 0.10, 900, 2000));
        file = new File(getClass().getClassLoader().getResource("audio/right-high-kick.mp3").getFile());
        audioRepository.save(new Audio(instructionRepository.findById(118L).get(), languageRepository.findById(1L).get(), Helper.getAudioFileLengthMillis(file), readFileToByteArray(file)));

    }

    private void seedSpeedOptions() {
        speedRepository.save(new Speed(0L, "extra slow", 2.25));
        speedRepository.save(new Speed(1L, "slow", 1.50));
        speedRepository.save(new Speed(2L, "normal", 1.0));
        speedRepository.save(new Speed(3L, "fast", 0.66));
        speedRepository.save(new Speed(4L, "extra fast", 0.4356));
    }

    private void seedFight() {
        for (Speed speed : speedRepository.findAll()) {
            int numberOfFightsByCriteria = fightRepository.findBySpeed(speed).size();
            for (int i = numberOfFightsByCriteria; i < 10; i++) {
                fightFactory.createFight(3, 1, speed);
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
