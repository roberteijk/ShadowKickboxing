/**
 * Created by Robert van den Eijk on 27-5-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.DefensiveMode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DefensiveModeRepository extends CrudRepository<DefensiveMode, Long> {

    List<DefensiveMode> findAll();

    DefensiveMode findByDescriptionIn2Chars(String descriptionIn2Chars);
}
