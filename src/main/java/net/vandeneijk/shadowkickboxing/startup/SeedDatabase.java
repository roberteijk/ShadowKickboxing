/**
 * Created by Robert van den Eijk on 23-4-2020.
 */

package net.vandeneijk.shadowkickboxing.startup;

import net.vandeneijk.shadowkickboxing.models.*;
import net.vandeneijk.shadowkickboxing.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class SeedDatabase {

    private static final Logger log = LoggerFactory.getLogger(SeedDatabase.class);

    @Bean
    @Order(1)
    public CommandLineRunner seedLanguage(LanguageRepository languageRepository) {
        return (args) -> {
            log.info("Seeding database with Language.");
            languageRepository.save(new Language(0L, "English"));
        };
    }

    @Bean
    @Order(2)
    public CommandLineRunner seedBodyPart(BodyPartRepository bodyPartRepository) {
        return (args) -> {
            log.info("Seeding database with BodyPart.");
            bodyPartRepository.save(new BodyPart(0L, "left arm"));
            bodyPartRepository.save(new BodyPart(1L, "right arm"));
            bodyPartRepository.save(new BodyPart(2L, "both arms"));
            bodyPartRepository.save(new BodyPart(3L, "left leg"));
            bodyPartRepository.save(new BodyPart(4L, "right leg"));
            bodyPartRepository.save(new BodyPart(5L, "both legs"));
            bodyPartRepository.save(new BodyPart(6L, "body"));
            bodyPartRepository.save(new BodyPart(7L, "head"));
        };
    }

    @Bean
    @Order(3)
    public CommandLineRunner seedHeight(HeightRepository heightRepository) {
        return (args) -> {
            log.info("Seeding database with Height.");
            heightRepository.save(new Height(0L, "high"));
            heightRepository.save(new Height(1L, "middle"));
            heightRepository.save(new Height(2L, "low"));
        };
    }

    @Bean
    @Order(4)
    public CommandLineRunner seedRuleSet(RuleSetRepository ruleSetRepository) {
        return (args) -> {
            log.info("Seeding database with RuleSet.");
            ruleSetRepository.save(new RuleSet(0L, "Glory Kickboxing"));
        };
    }

    @Bean
    @Order(5)
    public CommandLineRunner seedOffensiveMove(OffensiveMoveRepository offensiveMoveRepository, BodyPartRepository bodyPartRepository, HeightRepository heightRepository) {
        return (args) -> {
            log.info("Seeding database with OffensiveMove.");
            offensiveMoveRepository.save(new OffensiveMove(0L, "jab", bodyPartRepository.findById(0L).get(), heightRepository.findById(0L).get(), 250));
            offensiveMoveRepository.save(new OffensiveMove(1L, "double jab", bodyPartRepository.findById(0L).get(), heightRepository.findById(0L).get(), 400));
            offensiveMoveRepository.save(new OffensiveMove(2L, "cross", bodyPartRepository.findById(1L).get(), heightRepository.findById(0L).get(), 350));
            offensiveMoveRepository.save(new OffensiveMove(3L, "left hook head", bodyPartRepository.findById(0L).get(), heightRepository.findById(0L).get(), 500));
            offensiveMoveRepository.save(new OffensiveMove(4L, "right hook head", bodyPartRepository.findById(1L).get(), heightRepository.findById(0L).get(), 500));
            offensiveMoveRepository.save(new OffensiveMove(5L, "left hook body", bodyPartRepository.findById(0L).get(), heightRepository.findById(1L).get(), 500));
            offensiveMoveRepository.save(new OffensiveMove(6L, "right hook body", bodyPartRepository.findById(1L).get(), heightRepository.findById(1L).get(), 500));
            offensiveMoveRepository.save(new OffensiveMove(7L, "left uppercut", bodyPartRepository.findById(0L).get(), heightRepository.findById(0L).get(), 500));
            offensiveMoveRepository.save(new OffensiveMove(8L, "right uppercut", bodyPartRepository.findById(1L).get(), heightRepository.findById(0L).get(), 500));
            offensiveMoveRepository.save(new OffensiveMove(9L, "left low kick", bodyPartRepository.findById(3L).get(), heightRepository.findById(2L).get(), 750));
            offensiveMoveRepository.save(new OffensiveMove(10L, "right low kick", bodyPartRepository.findById(4L).get(), heightRepository.findById(2L).get(), 750));
            offensiveMoveRepository.save(new OffensiveMove(11L, "left middle kick", bodyPartRepository.findById(3L).get(), heightRepository.findById(1L).get(), 750));
            offensiveMoveRepository.save(new OffensiveMove(12L, "right middle kick", bodyPartRepository.findById(4L).get(), heightRepository.findById(1L).get(), 750));
            offensiveMoveRepository.save(new OffensiveMove(13L, "left high kick", bodyPartRepository.findById(3L).get(), heightRepository.findById(0L).get(), 1000));
            offensiveMoveRepository.save(new OffensiveMove(14L, "right high kick", bodyPartRepository.findById(4L).get(), heightRepository.findById(0L).get(), 1000));
            offensiveMoveRepository.save(new OffensiveMove(15L, "clinch left knee", bodyPartRepository.findById(6L).get(), heightRepository.findById(1L).get(), 1000));
            offensiveMoveRepository.save(new OffensiveMove(16L, "clinch right knee", bodyPartRepository.findById(6L).get(), heightRepository.findById(1L).get(), 1000));
        };
    }

    @Bean
    @Order(6)
    public CommandLineRunner seedBodyPartsDelay(BodyPartsDelayRepository bodyPartsDelayRepository, BodyPartRepository bodyPartRepository) {
        return (args) -> {
            log.info("Seeding database with BodyPartsDelay.");
            bodyPartsDelayRepository.save(new BodyPartsDelay(bodyPartRepository.findById(3L).get(),bodyPartRepository.findById(4L).get(), 500));
            bodyPartsDelayRepository.save(new BodyPartsDelay(bodyPartRepository.findById(4L).get(),bodyPartRepository.findById(3L).get(), 500));
        };
    }

    @Bean
    @Order(7)
    public CommandLineRunner seedCombo(ComboRepository comboRepository, RuleSetRepository ruleSetRepository, ComboOffensiveMoveRepository comboOffensiveMoveRepository, OffensiveMoveRepository offensiveMoveRepository) {
        return (args) -> {
            log.info("Seeding database with Combo and ComboOffensiveMove.");
            comboRepository.save(new Combo(0L, "jab", ruleSetRepository.findById(0L).get(), 1.0));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(0L).get(), offensiveMoveRepository.findById(0L).get(), 0));

            comboRepository.save(new Combo(1L, "double jab", ruleSetRepository.findById(0L).get(), 1.0));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(1L).get(), offensiveMoveRepository.findById(1L).get(), 0));

            comboRepository.save(new Combo(2L, "cross", ruleSetRepository.findById(0L).get(), 0.8));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(2L).get(), offensiveMoveRepository.findById(2L).get(), 0));

            comboRepository.save(new Combo(3L, "left hook head", ruleSetRepository.findById(0L).get(), 0.5));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(3L).get(), offensiveMoveRepository.findById(3L).get(), 0));

            comboRepository.save(new Combo(4L, "right hook head", ruleSetRepository.findById(0L).get(), 0.4));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(4L).get(), offensiveMoveRepository.findById(4L).get(), 0));

            comboRepository.save(new Combo(5L, "left hook body", ruleSetRepository.findById(0L).get(), 0.5));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(5L).get(), offensiveMoveRepository.findById(5L).get(), 0));

            comboRepository.save(new Combo(6L, "right hook body", ruleSetRepository.findById(0L).get(), 0.4));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(6L).get(), offensiveMoveRepository.findById(6L).get(), 0));

            comboRepository.save(new Combo(7L, "left uppercut", ruleSetRepository.findById(0L).get(), 0.5));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(7L).get(), offensiveMoveRepository.findById(7L).get(), 0));

            comboRepository.save(new Combo(8L, "right uppercut", ruleSetRepository.findById(0L).get(), 0.35));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(8L).get(), offensiveMoveRepository.findById(8L).get(), 0));

            comboRepository.save(new Combo(9L, "left low kick", ruleSetRepository.findById(0L).get(), 0.5));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(9L).get(), offensiveMoveRepository.findById(9L).get(), 0));

            comboRepository.save(new Combo(10L, "right low kick", ruleSetRepository.findById(0L).get(), 0.35));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(10L).get(), offensiveMoveRepository.findById(10L).get(), 0));

            comboRepository.save(new Combo(11L, "left middle kick", ruleSetRepository.findById(0L).get(), 0.2));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(11L).get(), offensiveMoveRepository.findById(11L).get(), 0));

            comboRepository.save(new Combo(12L, "right middle kick", ruleSetRepository.findById(0L).get(), 0.15));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(12L).get(), offensiveMoveRepository.findById(12L).get(), 0));

            comboRepository.save(new Combo(13L, "left high kick", ruleSetRepository.findById(0L).get(), 0.1));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(13L).get(), offensiveMoveRepository.findById(13L).get(), 0));

            comboRepository.save(new Combo(14L, "right high kick", ruleSetRepository.findById(0L).get(), 0.1));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(14L).get(), offensiveMoveRepository.findById(14L).get(), 0));

            comboRepository.save(new Combo(15L, "clinch left knee", ruleSetRepository.findById(0L).get(), 0.1));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(15L).get(), offensiveMoveRepository.findById(15L).get(), 0));

            comboRepository.save(new Combo(16L, "clinch right knee", ruleSetRepository.findById(0L).get(), 0.1));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(16L).get(), offensiveMoveRepository.findById(16L).get(), 0));

            comboRepository.save(new Combo(1000L, "double jab + cross", ruleSetRepository.findById(0L).get(), 0.5));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(1000L).get(), offensiveMoveRepository.findById(1L).get(), 0));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(1000L).get(), offensiveMoveRepository.findById(2L).get(), 1));

            comboRepository.save(new Combo(1001L, "double jab + right uppercut", ruleSetRepository.findById(0L).get(), 0.15));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(1001L).get(), offensiveMoveRepository.findById(1L).get(), 0));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(1001L).get(), offensiveMoveRepository.findById(8L).get(), 1));
            comboOffensiveMoveRepository.save(new ComboOffensiveMove(comboRepository.findById(1001L).get(), offensiveMoveRepository.findById(5L).get(), 2));
        };
    }

    @Bean
    @Order(8)
    public CommandLineRunner seedAudio(AudioRepository audioRepository, OffensiveMoveRepository offensiveMoveRepository, LanguageRepository languageRepository) {
        return (args) -> {
            log.info("Seeding database with Audio");
            audioRepository.save(new Audio(offensiveMoveRepository.findById(0L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/jab.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(1L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/double-jab.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(2L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/cross.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(3L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/left-hook-head.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(4L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/right-hook-head.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(5L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/left-hook-body.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(6L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/right-hook-body.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(7L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/left-uppercut.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(8L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/right-uppercut.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(9L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/left-low-kick.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(10L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/right-low-kick.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(11L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/left-middle-kick.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(12L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/right-middle-kick.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(13L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/left-high-kick.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(14L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/right-high-kick.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(15L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/clinch-left-knee.mp3").getFile()))));
            audioRepository.save(new Audio(offensiveMoveRepository.findById(16L).get(), languageRepository.findById(0L).get(), readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/clinch-right-knee.mp3").getFile()))));

        };
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
            log.error("Error reading audio file for seeding database. Exception: " + ioEx);
        }
        return new byte[0];
    }
}
