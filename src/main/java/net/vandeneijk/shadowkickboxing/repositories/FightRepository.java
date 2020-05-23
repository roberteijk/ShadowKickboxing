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

public interface FightRepository extends CrudRepository<Fight, Long> {

    Fight findByRandomId(String randomId);

    Fight findFirstBySpeedAndLengthAndZdtFirstDownload(Speed speed, Length length, ZonedDateTime zdtFirstDownload);

    long countBySpeedAndLength(Speed speed, Length length);

    boolean existsByRandomId(String randomId);

    @Transactional
    long deleteByZdtFirstDownloadBefore(ZonedDateTime zdt);
}
