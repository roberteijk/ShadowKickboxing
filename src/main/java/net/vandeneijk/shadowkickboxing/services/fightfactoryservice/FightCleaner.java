/**
 * Created by Robert van den Eijk on 23-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services.fightfactoryservice;

import net.vandeneijk.shadowkickboxing.models.Fight;
import net.vandeneijk.shadowkickboxing.services.FightAudioDataService;
import net.vandeneijk.shadowkickboxing.services.FightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@DependsOn({"taskExecutor"})
public class FightCleaner {

    private static final Logger logger = LoggerFactory.getLogger(FightCleaner.class);

    private final FightService fightService;
    private final FightAudioDataService fightAudioDataService;

    public FightCleaner(FightService fightService, FightAudioDataService fightAudioDataService) {
        this.fightService = fightService;
        this.fightAudioDataService = fightAudioDataService;
    }

    @Async
    public void clean() {
        ZonedDateTime zdtToDeleteBefore = ZonedDateTime.now().minusMinutes(2);

        List<Fight> fightListToRemove = fightService.findByZdtFirstDownloadBefore(zdtToDeleteBefore);
        for (Fight fightToRemove : fightListToRemove) {
            fightService.deleteById(fightToRemove.getFightId());
            fightAudioDataService.deleteById(fightToRemove.getFightAudioDataId());
        }

        if (fightListToRemove.size() > 0) logger.info("Outdated fights removed from database. Amount: " + fightListToRemove.size());
    }

    @Async
    public void removeOverdueReservation() {
        // TODO
    }
}
