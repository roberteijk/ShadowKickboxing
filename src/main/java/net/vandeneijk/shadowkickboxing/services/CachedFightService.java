/**
 * Created by Robert van den Eijk on 28-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.Fight;
import net.vandeneijk.shadowkickboxing.repositories.FightRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

@Service
public class CachedFightService {

    private FightRepository fightRepository;

    private final Map<String, ArrayDeque<Fight>> cachedFights = new HashMap<>();

    public CachedFightService(FightRepository fightRepository) {
        this.fightRepository = fightRepository;
    }

    public void save(Fight fight) {
        String fightSpeedCode = fight.getSpeed().getDescriptionIn2Chars();
        String fightLengthCode = fight.getLength().getDescriptionIn2Chars();
        String fightDefensiveModeCode = fight.getDefensiveMode().getDescriptionIn2Chars();
        String fightCode =  fightSpeedCode + fightLengthCode + fightDefensiveModeCode;

        if (!cachedFights.containsKey(fightCode)) cachedFights.put(fightCode, new ArrayDeque<>());

        ArrayDeque<Fight> currentFightSpecQueue = cachedFights.get(fightCode);
        if (currentFightSpecQueue.size() < 3) currentFightSpecQueue.push(fight);

        fightRepository.save(fight);
    }
}
