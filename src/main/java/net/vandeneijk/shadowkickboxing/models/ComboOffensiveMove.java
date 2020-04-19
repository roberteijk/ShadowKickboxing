/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(ComboOffensiveMoveId.class)
public class ComboOffensiveMove {

    @Id
    @ManyToOne
    private Combo comboForComboOffensiveMove;

    @Id
    @ManyToOne
    private OffensiveMove offensiveMoveForComboOffensiveMove;

    @Id
    private Integer sequenceIndex;

    protected ComboOffensiveMove() {}

    public ComboOffensiveMove(Combo comboForComboOffensiveMove, OffensiveMove offensiveMoveForComboOffensiveMove, Integer sequenceIndex) {
        this.comboForComboOffensiveMove = comboForComboOffensiveMove;
        this.offensiveMoveForComboOffensiveMove = offensiveMoveForComboOffensiveMove;
        this.sequenceIndex = sequenceIndex;
    }

    public Combo getComboForComboOffensiveMove() {
        return comboForComboOffensiveMove;
    }

    public OffensiveMove getOffensiveMoveForComboOffensiveMove() {
        return offensiveMoveForComboOffensiveMove;
    }

    public Integer getSequenceIndex() {
        return sequenceIndex;
    }
}
