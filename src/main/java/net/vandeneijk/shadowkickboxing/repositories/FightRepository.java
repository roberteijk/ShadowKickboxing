/**
 * Created by Robert van den Eijk on 22-4-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.Fight;
import net.vandeneijk.shadowkickboxing.models.Length;
import net.vandeneijk.shadowkickboxing.models.Speed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface FightRepository extends CrudRepository<Fight, Long> {

    Optional<Fight> findByRandomId(String randomId);

    Fight getByRandomId(String randomId);

    List<Fight> findBySpeedAndLengthAndZdtFirstDownload(Speed speed, Length length, ZonedDateTime zdtFirstDownload);

    long countBySpeedAndLength(Speed speed, Length length);

    boolean existsByRandomId(String randomId);

    @Transactional
    long deleteByZdtFirstDownloadBefore(ZonedDateTime zdt);
}
