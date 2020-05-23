/**
 * Created by Robert van den Eijk on 17-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.Language;
import net.vandeneijk.shadowkickboxing.repositories.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public void saveIfDescriptionUnique(Language language) {
        if (languageRepository.findByDescription(language.getDescription()).isPresent()) return;
        languageRepository.save(language);
    }

    public Optional<Language> findByDescription(String language) {
        return languageRepository.findByDescription(language);
    }
}
