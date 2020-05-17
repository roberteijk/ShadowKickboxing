/**
 * Created by Robert van den Eijk on 22-4-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.Fight;
import net.vandeneijk.shadowkickboxing.models.Length;
import net.vandeneijk.shadowkickboxing.models.Speed;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FightRepository extends CrudRepository<Fight, Long> {

    List<Fight> findBySpeedAndLength(Speed speed, Length length);

    long countBySpeedAndLength(Speed speed, Length length);
}
