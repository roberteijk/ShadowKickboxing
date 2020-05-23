/**
 * Created by Robert van den Eijk on 17-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.Audio;
import net.vandeneijk.shadowkickboxing.models.Instruction;
import net.vandeneijk.shadowkickboxing.models.Language;
import net.vandeneijk.shadowkickboxing.repositories.AudioRepository;
import org.springframework.stereotype.Service;

@Service
public class AudioService {

    private final AudioRepository audioRepository;

    public AudioService(AudioRepository audioRepository) {
        this.audioRepository = audioRepository;
    }

    public void save(Audio audio) {
        audioRepository.save(audio);
    }

    public Audio findByInstructionAndLanguage(Instruction instruction, Language language) {
        return audioRepository.findByInstructionAndLanguage(instruction, language);
    }
}
