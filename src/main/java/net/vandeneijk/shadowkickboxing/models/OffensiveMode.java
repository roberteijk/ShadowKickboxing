/**
 * Created by Robert van den Eijk on 27-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class OffensiveMode {

    @Id
    private Long offensiveModeId;

    @NotNull
    @Column(unique = true)
    private String description;

    @NotNull
    @Column(unique = true)
    private String descriptionIn2Chars;

    @NotNull
    private Boolean allowArms;

    @NotNull
    private Boolean allowLegs;

    protected OffensiveMode() {}

    public OffensiveMode(Long offensiveModeId, @NotNull String description, @NotNull String descriptionIn2Chars, @NotNull Boolean allowArms, @NotNull Boolean allowLegs) {
        this.offensiveModeId = offensiveModeId;
        this.description = description;
        this.descriptionIn2Chars = descriptionIn2Chars;
        this.allowArms = allowArms;
        this.allowLegs = allowLegs;
    }

    public Long getOffensiveModeId() {
        return offensiveModeId;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionIn2Chars() {
        return descriptionIn2Chars;
    }

    public Boolean getAllowArms() {
        return allowArms;
    }

    public Boolean getAllowLegs() {
        return allowLegs;
    }
}
