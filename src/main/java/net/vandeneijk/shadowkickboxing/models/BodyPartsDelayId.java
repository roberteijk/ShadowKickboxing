/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import java.io.Serializable;

public class BodyPartsDelayId implements Serializable {

    private Long bodyPartForDelayPrev;
    private Long bodyPartForDelayNext;

    protected BodyPartsDelayId() {}

    public BodyPartsDelayId(Long bodyPartForDelayPrev, Long bodyPartForDelayNext) {
        this.bodyPartForDelayPrev = bodyPartForDelayPrev;
        this.bodyPartForDelayNext = bodyPartForDelayNext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BodyPartsDelayId that = (BodyPartsDelayId) o;

        if (!bodyPartForDelayPrev.equals(that.bodyPartForDelayPrev)) return false;
        return bodyPartForDelayNext.equals(that.bodyPartForDelayNext);
    }

    @Override
    public int hashCode() {
        int result = bodyPartForDelayPrev.hashCode();
        result = 31 * result + bodyPartForDelayNext.hashCode();
        return result;
    }
}
