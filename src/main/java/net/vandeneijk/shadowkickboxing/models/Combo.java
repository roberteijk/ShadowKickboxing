/**
 * Created by Robert van den Eijk on 15-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Combo {

    @Id
    private Integer comboId;

    @NotNull
    private String description;

    @ManyToOne
    @NotNull
    private RuleSet ruleSetForCombo;

    @NotNull
    private Double callFrequencyWeight;

    @NotNull
    private Double timingMultiplier;

    @OneToMany(mappedBy = "comboForComboOffensiveMove")
    private Collection<ComboOffensiveMove> comboOffensiveMoves;

    protected Combo() {}

    public Combo(Integer comboId, String description, RuleSet ruleSetForCombo, Double callFrequencyWeight) {
        this(comboId, description, ruleSetForCombo, callFrequencyWeight, 1.0);
    }

    public Combo(Integer comboId, String description, RuleSet ruleSetForCombo, Double callFrequencyWeight, Double timingMultiplier) {
        this.comboId = comboId;
        this.description = description;
        this.ruleSetForCombo = ruleSetForCombo;
        this.callFrequencyWeight = callFrequencyWeight;
        this.timingMultiplier = timingMultiplier;
    }

    public Integer getComboId() {
        return comboId;
    }

    public String getDescription() {
        return description;
    }

    public RuleSet getRuleSetForCombo() {
        return ruleSetForCombo;
    }

    public Double getCallFrequencyWeight() {
        return callFrequencyWeight;
    }

    public Double getTimingMultiplier() {
        return timingMultiplier;
    }
}
