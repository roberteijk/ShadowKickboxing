/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.repositories;

import net.vandeneijk.shadowkickboxing.models.Length;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LengthRepository extends CrudRepository<Length, Long> {

    List<Length> findAll();
}
