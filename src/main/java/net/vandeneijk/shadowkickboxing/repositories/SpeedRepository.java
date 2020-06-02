/**
 * Created by Robert van den Eijk on 11-5-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.Speed;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpeedRepository extends CrudRepository<Speed, Long> {

    List<Speed> findAll();
}
