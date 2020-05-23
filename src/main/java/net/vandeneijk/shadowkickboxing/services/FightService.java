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

    public Fight findByRandomId(String randomId) {
        return fightRepository.findByRandomId(randomId);
    }

    public long countBySpeedAndLength(Speed speed, Length length) {
        return fightRepository.countBySpeedAndLength(speed, length);
    }

    public boolean existsByRandomId(String randomId) {
        return fightRepository.existsByRandomId(randomId);
    }

    public Fight retrieveFreshFight(Speed speed, Length length) {
        Fight fight;

        while ((fight = fightRepository.findFirstBySpeedAndLengthAndZdtFirstDownload(speed, length, null)) == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException iEx) {
                iEx.printStackTrace();
            }
        }

        return fight;
    }

    public long deleteByZdtFirstDownloadBefore(ZonedDateTime zdt) {
        return fightRepository.deleteByZdtFirstDownloadBefore(zdt);

    }
}
