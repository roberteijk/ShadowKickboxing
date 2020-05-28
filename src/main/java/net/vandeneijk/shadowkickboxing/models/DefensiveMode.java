/**
 * Created by Robert van den Eijk on 27-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class DefensiveMode {

    @Id
    private Long defensiveModeId;

    @NotNull
    private String description;

    @NotNull
    private String descriptionIn2Chars;

    @NotNull
    private Boolean allowBlock;

    @NotNull
    private Boolean allowEvade;

    @OneToMany(mappedBy = "defensiveMode")
    private Collection<Fight> fightCollection;

    protected DefensiveMode() {}

    public DefensiveMode(Long defensiveModeId, @NotNull String description, @NotNull String descriptionIn2Chars, @NotNull Boolean allowBlock, @NotNull Boolean allowEvade) {
        this.defensiveModeId = defensiveModeId;
        this.description = description;
        this.descriptionIn2Chars = descriptionIn2Chars;
        this.allowBlock = allowBlock;
        this.allowEvade = allowEvade;
    }

    public Long getDefensiveModeId() {
        return defensiveModeId;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionIn2Chars() {
        return descriptionIn2Chars;
    }

    public Boolean isAllowBlock() {
        return allowBlock;
    }

    public Boolean isAllowEvade() {
        return allowEvade;
    }
}
