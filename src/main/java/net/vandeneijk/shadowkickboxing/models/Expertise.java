/**
 * Created by Robert van den Eijk on 1-6-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Expertise {

    @Id
    private Long expertiseId;

    @NotNull
    private String description;

    @NotNull
    private String descriptionIn2Chars;

    @NotNull
    private Boolean allowUpperBody;

    @NotNull
    private Boolean allowLowerBody;

    @OneToMany(mappedBy = "expertise")
    private Collection<Fight> fightCollection;

    protected Expertise() {}

    public Expertise(Long ExpertiseId, @NotNull String description, @NotNull String descriptionIn2Chars, @NotNull Boolean allowUpperBody, @NotNull Boolean allowLowerBody) {
        this.expertiseId = ExpertiseId;
        this.description = description;
        this.descriptionIn2Chars = descriptionIn2Chars;
        this.allowUpperBody = allowUpperBody;
        this.allowLowerBody = allowLowerBody;
    }

    public Long getExpertiseId() {
        return expertiseId;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionIn2Chars() {
        return descriptionIn2Chars;
    }

    public Boolean isAllowUpperBody() {
        return allowUpperBody;
    }

    public Boolean isAllowLowerBody() {
        return allowLowerBody;
    }
}
