/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.ConnectionLog;
import org.springframework.data.repository.CrudRepository;

public interface ConnectionLogRepository extends CrudRepository<ConnectionLog, Long> {
}
