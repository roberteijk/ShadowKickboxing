/**
 * Created by Robert van den Eijk on 1-6-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.BodyHalf;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BodyHalfRepository extends CrudRepository<BodyHalf, Long> {

    List<BodyHalf> findAll();
}
