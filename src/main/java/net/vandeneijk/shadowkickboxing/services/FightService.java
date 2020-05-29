/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.DefensiveMode;
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

    private final CachedFightService cachedFightService;
    private final FightRepository fightRepository;
    private final SpeedRepository speedRepository;
    private final LengthRepository lengthRepository;

    public FightService(CachedFightService cachedFightService, FightRepository fightRepository, SpeedRepository speedRepository, LengthRepository lengthRepository) {
        this.cachedFightService = cachedFightService;
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

    public long countBySpeedAndLengthAndDefensiveMove(Speed speed, Length length, DefensiveMode defensiveMode) {
        return fightRepository.countBySpeedAndLengthAndDefensiveMode(speed, length, defensiveMode);
    }

    public boolean existsByRandomId(String randomId) {
        return fightRepository.existsByRandomId(randomId);
    }

    public synchronized Fight retrieveFreshFight(Speed speed, Length length, DefensiveMode defensiveMode) {
        Fight fight;

        while ((fight = fightRepository.findFirstBySpeedAndLengthAndDefensiveModeAndZdtReservationAndZdtFirstDownload(speed, length, defensiveMode, null,null)) == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException iEx) {
                iEx.printStackTrace();
            }
        }

        fight.setZdtReservation(ZonedDateTime.now());
        fightRepository.save(fight);
        return fight;
    }

    public long deleteByZdtFirstDownloadBefore(ZonedDateTime zdt) {
        return fightRepository.deleteByZdtFirstDownloadBefore(zdt);

    }

    public Fight getFight(String fileName) {
        String fightRandomId;
        String fightSpeedCode;
        String fightLengthCode;
        String fightDefensiveModeCode;

        try {
            if (!fileName.startsWith("skb_")) return null;
            else if (!fileName.substring(18).equals(".mp3")) return null;
            else if (fileName.length() != 22) return null;
            fightRandomId = fileName.substring(4, 12);
            fightSpeedCode = fileName.substring(12, 14);
            fightLengthCode = fileName.substring(14, 16);
            fightDefensiveModeCode = fileName.substring(16, 18);
        } catch (IndexOutOfBoundsException ioobEx) {
            return null;
        }

        Fight fight = findByRandomId(fightRandomId);
        if (fight != null && fight.getSpeed().getDescriptionIn2Chars().equals(fightSpeedCode) && fight.getLength().getDescriptionIn2Chars().equals(fightLengthCode)  && fight.getDefensiveMode().getDescriptionIn2Chars().equals(fightDefensiveModeCode)) {
            if (fight.getZdtFirstDownload() == null) {
                fight.setZdtFirstDownload(ZonedDateTime.now());
                save(fight);
            }

            return fight;
        }

        return null;
    }

}
