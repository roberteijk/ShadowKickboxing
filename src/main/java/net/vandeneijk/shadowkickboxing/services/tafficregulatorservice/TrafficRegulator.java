/**
 * Created by Robert van den Eijk on 26-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services.tafficregulatorservice;

import net.vandeneijk.shadowkickboxing.startup.SeedDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

@Service
public class TrafficRegulator {

    private static final Logger logger = LoggerFactory.getLogger(SeedDatabase.class);

    private final Map<String, Map<String, Map<Integer, ArrayDeque<ZonedDateTime>>>> interactionsOfAllClientsOfAllPageIds = new HashMap<>();

    public boolean isTrafficAllowed(String pageId, String trafficId, ZonedDateTime zdtToLog , int ... maxAllowedTrafficPerMagnitudeOfSecond) {
        if (maxAllowedTrafficPerMagnitudeOfSecond.length > 7) throw new IllegalArgumentException("Only a maximum of 7 magnitudes is allowed.");

        Map<Integer, ArrayDeque<ZonedDateTime>> interActionsOfOneClient = getMapByPageIdAndTrafficId(pageId, trafficId);

        boolean returnValue = true;
        for (int i = 0; i < maxAllowedTrafficPerMagnitudeOfSecond.length; i++) {
            int lengthOfPeriod = (int) Math.pow(10, i);

            ArrayDeque<ZonedDateTime> timeLog;
            if ((timeLog = interActionsOfOneClient.get(lengthOfPeriod)) == null) {
                timeLog = new ArrayDeque<>();
                interActionsOfOneClient.put(lengthOfPeriod, timeLog);
            }

            timeLog.push(zdtToLog);

            while (timeLog.getLast().isBefore(ZonedDateTime.now().minusSeconds(lengthOfPeriod))) timeLog.removeLast();
            if (timeLog.size() > maxAllowedTrafficPerMagnitudeOfSecond[i]) returnValue = false;

        }

        if (!returnValue) logger.warn("TrafficRegulator denies " + trafficId + " for " + pageId + ". Reason: Exceeded traffic limits.");
        return returnValue;
    }

    private Map<Integer, ArrayDeque<ZonedDateTime>> getMapByPageIdAndTrafficId(String pageId, String trafficId) {
        Map<String, Map<Integer, ArrayDeque<ZonedDateTime>>> interactionsOfAllClients;
        if ((interactionsOfAllClients = interactionsOfAllClientsOfAllPageIds.get(pageId)) == null) {
            interactionsOfAllClients = new HashMap<>();
            interactionsOfAllClientsOfAllPageIds.put(pageId, interactionsOfAllClients);
        }

        Map<Integer, ArrayDeque<ZonedDateTime>> interActionsOfOneClient;
        if ((interActionsOfOneClient = interactionsOfAllClients.get(trafficId)) == null) {
            interActionsOfOneClient = new HashMap<>();
            interactionsOfAllClients.put(trafficId, interActionsOfOneClient);
        }

        return interActionsOfOneClient;
    }
}
