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
public class BodyHalf {

    @Id
    private Long bodyHalfId;

    @NotNull
    private String description;

    @NotNull
    private String descriptionIn2Chars;

    @NotNull
    private Boolean allowUpperBody;

    @NotNull
    private Boolean allowLowerBody;

    @OneToMany(mappedBy = "bodyHalf")
    private Collection<Fight> fightCollection;

    protected BodyHalf() {}

    public BodyHalf(Long bodyHalfId, @NotNull String description, @NotNull String descriptionIn2Chars, @NotNull Boolean allowUpperBody, @NotNull Boolean allowLowerBody) {
        this.bodyHalfId = bodyHalfId;
        this.description = description;
        this.descriptionIn2Chars = descriptionIn2Chars;
        this.allowUpperBody = allowUpperBody;
        this.allowLowerBody = allowLowerBody;
    }

    public Long getBodyHalfId() {
        return bodyHalfId;
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
