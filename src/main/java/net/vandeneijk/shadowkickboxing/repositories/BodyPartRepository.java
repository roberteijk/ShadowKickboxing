/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.BodyPart;
import org.springframework.data.repository.CrudRepository;

public interface BodyPartRepository extends CrudRepository<BodyPart, Long> {

}
