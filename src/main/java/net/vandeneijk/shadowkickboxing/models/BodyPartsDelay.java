/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(BodyPartsDelayId.class)
public class BodyPartsDelay {

    @Id
    @ManyToOne
    private BodyPart bodyPartForDelayPrev;

    @Id
    @ManyToOne
    private BodyPart bodyPartForDelayNext;

    @NotNull
    private Integer minDelayMillis;

    protected BodyPartsDelay() {}

    public BodyPartsDelay(BodyPart bodyPartForDelayPrev, BodyPart bodyPartForDelayNext, Integer minDelayMillis) {
        this.bodyPartForDelayPrev = bodyPartForDelayPrev;
        this.bodyPartForDelayNext = bodyPartForDelayNext;
        this.minDelayMillis = minDelayMillis;
    }

    public BodyPart getBodyPartForDelayPrev() {
        return bodyPartForDelayPrev;
    }

    public BodyPart getBodyPartForDelayNext() {
        return bodyPartForDelayNext;
    }

    public Integer getMinDelayMillis() {
        return minDelayMillis;
    }
}
