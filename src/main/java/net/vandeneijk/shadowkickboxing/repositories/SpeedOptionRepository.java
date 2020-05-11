/**
 * Created by Robert van den Eijk on 11-5-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.SpeedOption;
import org.springframework.data.repository.CrudRepository;

public interface SpeedOptionRepository extends CrudRepository<SpeedOption, Long> {
}
