/**
 * Created by Robert van den Eijk on 30-6-2020.
 */

package net.vandeneijk.shadowkickboxing.poststartup;

import net.vandeneijk.shadowkickboxing.models.DefensiveMode;
import net.vandeneijk.shadowkickboxing.models.Expertise;
import net.vandeneijk.shadowkickboxing.models.Length;
import net.vandeneijk.shadowkickboxing.models.Speed;
import net.vandeneijk.shadowkickboxing.services.*;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightFactory;
import net.vandeneijk.shadowkickboxing.services.fightfactoryservice.FightFactoryJob;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class SeedDatabasePostStartup implements ApplicationListener<ApplicationReadyEvent> {
    
    private final InstructionService instructionService;
    private final SpeedService speedService;
    private final LengthService lengthService;
    private final DefensiveModeService defensiveModeService;
    private final ExpertiseService expertiseService;
    private final FightService fightService;
    private final FightFactory fightFactory;

    public SeedDatabasePostStartup(InstructionService instructionService, SpeedService speedService, LengthService lengthService, DefensiveModeService defensiveModeService, ExpertiseService expertiseService, FightService fightService, FightFactory fightFactory) {
        this.instructionService = instructionService;
        this.speedService = speedService;
        this.lengthService = lengthService;
        this.defensiveModeService = defensiveModeService;
        this.expertiseService = expertiseService;
        this.fightService = fightService;
        this.fightFactory = fightFactory;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        seedFight();
    }

    private void seedFight() {
        Map<Integer, Runnable> fightsToCreate = new TreeMap<>();
        int count = 0;

        for (int i = 1; i <= 1; i++) {
            for (Speed speed : speedService.findAll()) {
                for (Length length : lengthService.findAll()) {
                    for (DefensiveMode defensiveMode : defensiveModeService.findAll()) {
                        for (Expertise expertise : expertiseService.findAll()) {
                            long numberOfFightsByCriteria = fightService.countBySpeedAndLengthAndDefensiveModeAndExpertiseAndZdtFirstDownload(speed, length, defensiveMode, expertise, null);
                            if (numberOfFightsByCriteria < i) fightsToCreate.put(count++, () -> {fightFactory.createFight(new FightFactoryJob("English", instructionService.findAll(), expertise, defensiveMode, speed, length));});
                        }
                    }
                }
            }
        }

        fightsToCreate.forEach((k, v) -> {v.run();});
    }
}
