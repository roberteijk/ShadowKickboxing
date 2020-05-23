/**
 * Created by Robert van den Eijk on 23-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services.fightfactoryservice;

import net.vandeneijk.shadowkickboxing.services.FightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@DependsOn({"taskExecutor"})
public class FightCleaner {

    private static final Logger logger = LoggerFactory.getLogger(FightCleaner.class);

    private final FightService fightService;

    public FightCleaner(FightService fightService) {
        this.fightService = fightService;
    }

    @Async
    public void clean() {
        ZonedDateTime zdtToDeleteBefore = ZonedDateTime.now().minusDays(2);
        long amountRemoved = fightService.deleteByZdtFirstDownloadBefore(zdtToDeleteBefore);
        if (amountRemoved > 0) logger.info("Outdated fights removed from database. Amount: " + amountRemoved);
    }
}
