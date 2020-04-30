/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.Combo;
import org.springframework.data.repository.CrudRepository;

public interface ComboRepository extends CrudRepository<Combo, Long> {

}
