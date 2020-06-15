/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.*;
import net.vandeneijk.shadowkickboxing.repositories.FightAudioDataRepository;
import net.vandeneijk.shadowkickboxing.repositories.FightRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class FightService {

    private final FightRepository fightRepository;
    private final FightAudioDataRepository fightAudioDataRepository;

    public FightService(FightRepository fightRepository, FightAudioDataRepository fightAudioDataRepository) {
        this.fightRepository = fightRepository;
        this.fightAudioDataRepository = fightAudioDataRepository;
    }

    public Fight findByRandomId(String randomId) {
        return fightRepository.findByRandomId(randomId);
    }

    public long countBySpeedAndLengthAndDefensiveModeAndExpertiseAndZdtFirstDownload(Speed speed, Length length, DefensiveMode defensiveMode, Expertise expertise, ZonedDateTime zdtFirstDownload) {
        return fightRepository.countBySpeedAndLengthAndDefensiveModeAndExpertiseAndZdtFirstDownload(speed, length, defensiveMode, expertise,zdtFirstDownload);
    }

    public boolean existsByRandomId(String randomId) {
        return fightRepository.existsByRandomId(randomId);
    }

    public byte[] getFightAudioData(Fight fight) {
        return fightAudioDataRepository.findById(fight.getFightAudioDataId()).get().getAudioFragment();
    }

    public synchronized Fight retrieveFreshFight(Speed speed, Length length, DefensiveMode defensiveMode, Expertise expertise) {
        Fight fight;

        if ((fight = fightRepository.findFirstBySpeedAndLengthAndDefensiveModeAndExpertiseAndZdtFirstDownload(speed, length, defensiveMode, expertise, null)) == null) return null;

        fight.setZdtReservedUntil(ZonedDateTime.now().plusSeconds(10));
        fightRepository.save(fight);

        return fight;
    }

    public synchronized Fight getFight(String fileName) {
        String fightRandomId;
        String fightSpeedCode;
        String fightLengthCode;
        String fightDefensiveModeCode;
        String fightBodyHalfCode;

        try {
            if (!fileName.startsWith("skb_")) return null;
            else if (!fileName.substring(20).equals(".mp3")) return null;
            else if (fileName.length() != 24) return null;
            fightRandomId = fileName.substring(4, 12);
            fightSpeedCode = fileName.substring(12, 14);
            fightLengthCode = fileName.substring(14, 16);
            fightDefensiveModeCode = fileName.substring(16, 18);
            fightBodyHalfCode = fileName.substring(18, 20);
        } catch (IndexOutOfBoundsException ioobEx) {
            return null;
        }

        Fight fight = findByRandomId(fightRandomId);
        if (fight != null && fight.getSpeed().getDescriptionIn2Chars().equals(fightSpeedCode) && fight.getLength().getDescriptionIn2Chars().equals(fightLengthCode)  && fight.getDefensiveMode().getDescriptionIn2Chars().equals(fightDefensiveModeCode) && fight.getExpertise().getDescriptionIn2Chars().equals(fightBodyHalfCode)) {
            if (fight.getZdtFirstDownload() == null) {
                fight.setZdtFirstDownload(ZonedDateTime.now());
                save(fight);
            }

            return fight;
        }

        return null;
    }

    public synchronized long deleteAllFightsBeforeZdtFirstDownload(ZonedDateTime zdtToDeleteBefore) {
        List<Fight> fightListToRemove = fightRepository.findByZdtFirstDownloadBefore(zdtToDeleteBefore);

        for (Fight fightToRemove : fightListToRemove) {
            fightRepository.deleteById(fightToRemove.getFightId());
            fightAudioDataRepository.deleteById(fightToRemove.getFightAudioDataId());
        }

        return fightListToRemove.size();
    }

    public void save(Fight fight) {
        fightRepository.save(fight);
    }

    public void save(Fight fight, FightAudioData fightAudioData) {
        fightAudioDataRepository.save(fightAudioData);
        fight.setFightAudioDataId(fightAudioData.getFightAudioDataId());
        fightRepository.save(fight);
    }
}
