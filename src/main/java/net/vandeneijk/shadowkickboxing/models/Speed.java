/**
 * Created by Robert van den Eijk on 11-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Speed implements InstructionPostModifier {

    @Id
    private Long speedId;

    @NotNull
    @Column(unique = true)
    private String description;

    @NotNull
    @Column(unique = true)
    private String descriptionIn2Chars;

    @NotNull
    private Double executionMillisMultiplier;

    @OneToMany(mappedBy = "speed")
    private Collection<Fight> speedCollection;

    protected Speed() {}

    public Speed(Long speedId, @NotNull String description, @NotNull String descriptionIn2Chars, @NotNull Double executionMillisMultiplier) {
        this.speedId = speedId;
        this.description = description;
        this.descriptionIn2Chars = descriptionIn2Chars;
        this.executionMillisMultiplier = executionMillisMultiplier;
    }

    public Long getSpeedId() {
        return speedId;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionIn2Chars() {
        return descriptionIn2Chars;
    }

    public Double getExecutionMillisMultiplier() {
        return executionMillisMultiplier;
    }
}
