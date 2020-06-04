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

    Fight findFirstBySpeedAndLengthAndDefensiveModeAndExpertiseAndZdtFirstDownload(Speed speed, Length length, DefensiveMode defensiveMode, Expertise expertise, ZonedDateTime zdtFirstDownload);

    long countBySpeedAndLengthAndDefensiveModeAndExpertiseAndZdtFirstDownload(Speed speed, Length length, DefensiveMode defensiveMode, Expertise expertise, ZonedDateTime zdtFirstDownload);

    boolean existsByRandomId(String randomId);

    List<Fight> findByZdtFirstDownloadBefore(ZonedDateTime zdt);
}
