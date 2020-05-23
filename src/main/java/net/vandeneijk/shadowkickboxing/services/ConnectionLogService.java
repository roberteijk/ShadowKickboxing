/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.services;

import net.vandeneijk.shadowkickboxing.models.ConnectionLog;
import net.vandeneijk.shadowkickboxing.repositories.ConnectionLogRepository;
import org.springframework.stereotype.Service;

@Service
public class ConnectionLogService {

    private final ConnectionLogRepository connectionLogRepository;

    public ConnectionLogService(ConnectionLogRepository connectionLogRepository) {
        this.connectionLogRepository = connectionLogRepository;
    }

    public void save(ConnectionLog connectionLog) {
        connectionLogRepository.save(connectionLog);
    }
}
