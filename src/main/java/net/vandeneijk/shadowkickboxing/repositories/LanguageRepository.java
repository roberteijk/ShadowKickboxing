/**
 * Created by Robert van den Eijk on 22-4-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.Language;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LanguageRepository extends CrudRepository<Language, Long> {

    Optional<Language> findByDescription(String language);
}
