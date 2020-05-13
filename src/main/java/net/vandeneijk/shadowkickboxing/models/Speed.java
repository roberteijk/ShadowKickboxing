/**
 * Created by Robert van den Eijk on 11-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Speed {

    @Id
    private Long speedId;

    @NotNull
    private String description;

    @NotNull
    private Double executionMillisMultiplier;

    @OneToMany(mappedBy = "speed")
    private Collection<Fight> speedCollection;

    protected Speed() {}

    public Speed(Long speedId, @NotNull String description, @NotNull Double executionMillisMultiplier) {
        this.speedId = speedId;
        this.description = description;
        this.executionMillisMultiplier = executionMillisMultiplier;
    }

    public Long getSpeedId() {
        return speedId;
    }

    public String getDescription() {
        return description;
    }

    public Double getExecutionMillisMultiplier() {
        return executionMillisMultiplier;
    }
}
