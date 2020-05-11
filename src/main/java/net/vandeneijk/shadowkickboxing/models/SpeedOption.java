/**
 * Created by Robert van den Eijk on 11-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class SpeedOption {

    @Id
    private Long speedOptionId;

    @NotNull
    private String description;

    @NotNull
    private Double executionMillisMultiplier;

    protected SpeedOption() {}

    public SpeedOption(Long speedOptionId, @NotNull String description, @NotNull Double executionMillisMultiplier) {
        this.speedOptionId = speedOptionId;
        this.description = description;
        this.executionMillisMultiplier = executionMillisMultiplier;
    }

    public Long getSpeedOptionId() {
        return speedOptionId;
    }

    public String getDescription() {
        return description;
    }

    public Double getExecutionMillisMultiplier() {
        return executionMillisMultiplier;
    }
}
