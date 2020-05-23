/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.Fight;
import net.vandeneijk.shadowkickboxing.models.Length;
import net.vandeneijk.shadowkickboxing.models.Speed;
import net.vandeneijk.shadowkickboxing.repositories.FightRepository;
import net.vandeneijk.shadowkickboxing.repositories.LengthRepository;
import net.vandeneijk.shadowkickboxing.repositories.SpeedRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FightService {

    private final FightRepository fightRepository;
    private final SpeedRepository speedRepository;
    private final LengthRepository lengthRepository;

    public FightService(FightRepository fightRepository, SpeedRepository speedRepository, LengthRepository lengthRepository) {
        this.fightRepository = fightRepository;
        this.speedRepository = speedRepository;
        this.lengthRepository = lengthRepository;
    }

    public void save(Fight fight) {
        fightRepository.save(fight);
    }

    public Optional<Fight> findById(Long id) {
        return fightRepository.findById(id);
    }

    public Optional<Fight> findByRandomId(String randomId) {
        return fightRepository.findByRandomId(randomId);
    }

    public Fight getByRandomId(String randomId) {
        return fightRepository.getByRandomId(randomId);
    }

    public long countBySpeedAndLength(Speed speed, Length length) {
        return fightRepository.countBySpeedAndLength(speed, length);
    }

    public boolean existsByRandomId(String randomId) {
        return fightRepository.existsByRandomId(randomId);
    }

    public Fight retrieveFreshFight(Speed speed, Length length) {
        List<Fight> fightList;

        while ((fightList = fightRepository.findBySpeedAndLengthAndZdtFirstDownload(speed, length, null)).size() == 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException iEx) {
                iEx.printStackTrace();
            }
        }

        return fightList.get(0);
    }

    public long deleteByZdtFirstDownloadBefore(ZonedDateTime zdt) {
        return fightRepository.deleteByZdtFirstDownloadBefore(zdt);

    }
}
