/**
 * Created by Robert van den Eijk on 1-6-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.Expertise;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpertiseRepository extends CrudRepository<Expertise, Long> {

    List<Expertise> findAll();

    Expertise findByDescriptionIn2Chars(String descriptionIn2Chars);
}
