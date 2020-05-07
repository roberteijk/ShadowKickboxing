/**
 * Created by Robert van den Eijk on 23-4-2020.
 */

package net.vandeneijk.shadowkickboxing.startup;

import net.vandeneijk.shadowkickboxing.models.Audio;
import net.vandeneijk.shadowkickboxing.models.Instruction;
import net.vandeneijk.shadowkickboxing.models.Language;
import net.vandeneijk.shadowkickboxing.repositories.AudioRepository;
import net.vandeneijk.shadowkickboxing.repositories.InstructionRepository;
import net.vandeneijk.shadowkickboxing.repositories.LanguageRepository;
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
    public CommandLineRunner seedInstruction(InstructionRepository instructionRepository, AudioRepository audioRepository, LanguageRepository languageRepository) {
        return (args) -> {
            log.info("Seeding database with Instruction and Audio.");
            instructionRepository.save(new Instruction(0L, "jab", true, 1.0, 500, 1000));
            audioRepository.save(new Audio(instructionRepository.findById(0L).get(), languageRepository.findById(0L).get(), 0, readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/jab.mp3").getFile()))));

            instructionRepository.save(new Instruction(1L, "double jab", true, 1.0, 800, 2000));
            audioRepository.save(new Audio(instructionRepository.findById(1L).get(), languageRepository.findById(0L).get(), 0, readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/double-jab.mp3").getFile()))));

            instructionRepository.save(new Instruction(2L, "cross", true, 0.6, 700, 1500));
            audioRepository.save(new Audio(instructionRepository.findById(2L).get(), languageRepository.findById(0L).get(), 0, readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/cross.mp3").getFile()))));


            instructionRepository.save(new Instruction(3L, "left hook head", true, 0.4, 800, 1500));
            audioRepository.save(new Audio(instructionRepository.findById(3L).get(), languageRepository.findById(0L).get(), 0, readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/left-hook-head.mp3").getFile()))));


            instructionRepository.save(new Instruction(4L, "right hook head", true, 0.4, 800, 1500));
            audioRepository.save(new Audio(instructionRepository.findById(4L).get(), languageRepository.findById(0L).get(), 0, readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/right-hook-head.mp3").getFile()))));


            instructionRepository.save(new Instruction(5L, "left hook body", true, 0.3, 800, 1500));
            audioRepository.save(new Audio(instructionRepository.findById(5L).get(), languageRepository.findById(0L).get(), 0, readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/left-hook-body.mp3").getFile()))));


            instructionRepository.save(new Instruction(6L, "right hook body", true, 0.30, 800, 1500));
            audioRepository.save(new Audio(instructionRepository.findById(6L).get(), languageRepository.findById(0L).get(), 0, readFileToByteArray(new File(getClass().getClassLoader().getResource("audio/right-hook-body.mp3").getFile()))));
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
