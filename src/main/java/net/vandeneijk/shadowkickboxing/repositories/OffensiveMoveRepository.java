/**
 * Created by Robert van den Eijk on 22-4-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.OffensiveMove;
import org.springframework.data.repository.CrudRepository;

public interface OffensiveMoveRepository extends CrudRepository<OffensiveMove, Long> {
}
