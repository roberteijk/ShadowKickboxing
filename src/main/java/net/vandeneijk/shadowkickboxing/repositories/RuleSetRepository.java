/**
 * Created by Robert van den Eijk on 22-4-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.RuleSet;
import org.springframework.data.repository.CrudRepository;

public interface RuleSetRepository extends CrudRepository<RuleSet, Long> {
}
