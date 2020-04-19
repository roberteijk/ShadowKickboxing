/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class BodyPart {

    @Id
    private Integer bodyPartId;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "bodyPartForOffensiveMove")
    private Collection<OffensiveMove> offensiveMoves;

    @OneToMany(mappedBy = "bodyPartForDelayPrev")
    private Collection<BodyPartsDelay> bodyPartsForDelayPrev;

    @OneToMany(mappedBy = "bodyPartForDelayNext")
    private Collection<BodyPartsDelay> bodyPartsForDelayNext;

    protected BodyPart() {}

    public BodyPart(Integer bodyPartId, String description) {
        this.bodyPartId = bodyPartId;
        this.description = description;
    }

    public Integer getBodyPartId() {
        return bodyPartId;
    }

    public String getDescription() {
        return description;
    }

    public Collection<OffensiveMove> getOffensiveMoves() {
        return offensiveMoves;
    }

    public void setOffensiveMoves(Collection<OffensiveMove> offensiveMoves) {
        this.offensiveMoves = offensiveMoves;
    }

    public Collection<BodyPartsDelay> getBodyPartsForDelayPrev() {
        return bodyPartsForDelayPrev;
    }

    public void setBodyPartsForDelayPrev(Collection<BodyPartsDelay> bodyPartsForDelayPrev) {
        this.bodyPartsForDelayPrev = bodyPartsForDelayPrev;
    }

    public Collection<BodyPartsDelay> getBodyPartsForDelayNext() {
        return bodyPartsForDelayNext;
    }

    public void setBodyPartsForDelayNext(Collection<BodyPartsDelay> bodyPartsForDelayNext) {
        this.bodyPartsForDelayNext = bodyPartsForDelayNext;
    }
}
