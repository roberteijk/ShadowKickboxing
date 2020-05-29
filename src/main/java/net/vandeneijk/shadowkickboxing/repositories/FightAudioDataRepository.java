/**
 * Created by Robert van den Eijk on 29-5-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.FightAudioData;
import org.springframework.data.repository.CrudRepository;

public interface FightAudioDataRepository extends CrudRepository<FightAudioData, Long> {}
