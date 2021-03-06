/**
 * Created by Robert van den Eijk on 23-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services.fightfactoryservice;

import net.vandeneijk.shadowkickboxing.services.FightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@DependsOn({"taskExecutor"})
public class FightCleaner {

    private static final Logger logger = LoggerFactory.getLogger(FightCleaner.class);

    private final FightService fightService;

    public FightCleaner(FightService fightService) {
        this.fightService = fightService;
    }

    @Async
    public void clean() {
        ZonedDateTime zdtToDeleteBefore = ZonedDateTime.now().minusHours(4);
        long numberOfFightsDeleted = fightService.deleteAllFightsBeforeZdtFirstDownload(zdtToDeleteBefore);

        if (numberOfFightsDeleted > 0) logger.info("Outdated fights removed from database. Amount: " + numberOfFightsDeleted);
    }
}
