/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import java.io.Serializable;

public class ComboOffensiveMoveId implements Serializable {

    private Combo comboForComboOffensiveMove;
    private OffensiveMove offensiveMoveForComboOffensiveMove;
    private Integer sequenceIndex;

    protected ComboOffensiveMoveId() {}

    public ComboOffensiveMoveId(Combo comboForComboOffensiveMove, OffensiveMove offensiveMoveForComboOffensiveMove, Integer sequenceIndex) {
        this.comboForComboOffensiveMove = comboForComboOffensiveMove;
        this.offensiveMoveForComboOffensiveMove = offensiveMoveForComboOffensiveMove;
        this.sequenceIndex = sequenceIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComboOffensiveMoveId that = (ComboOffensiveMoveId) o;

        if (!comboForComboOffensiveMove.equals(that.comboForComboOffensiveMove)) return false;
        if (!offensiveMoveForComboOffensiveMove.equals(that.offensiveMoveForComboOffensiveMove)) return false;
        return sequenceIndex.equals(that.sequenceIndex);
    }

    @Override
    public int hashCode() {
        int result = comboForComboOffensiveMove.hashCode();
        result = 31 * result + offensiveMoveForComboOffensiveMove.hashCode();
        result = 31 * result + sequenceIndex.hashCode();
        return result;
    }
}
