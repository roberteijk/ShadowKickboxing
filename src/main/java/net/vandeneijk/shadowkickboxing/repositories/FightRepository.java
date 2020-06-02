/**
 * Created by Robert van den Eijk on 22-4-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.*;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface FightRepository extends CrudRepository<Fight, Long> {

    Fight findByRandomId(String randomId);

    Fight findFirstBySpeedAndLengthAndDefensiveModeAndBodyHalfAndZdtFirstDownload(Speed speed, Length length, DefensiveMode defensiveMode, BodyHalf bodyHalf, ZonedDateTime zdtFirstDownload);

    long countBySpeedAndLengthAndDefensiveModeAndBodyHalfAndZdtFirstDownload(Speed speed, Length length, DefensiveMode defensiveMode, BodyHalf bodyHalf, ZonedDateTime zdtFirstDownload);

    boolean existsByRandomId(String randomId);

//    @Transactional
//    long deleteByZdtFirstDownloadBefore(ZonedDateTime zdt);

    List<Fight> findByZdtFirstDownloadBefore(ZonedDateTime zdt);
}
