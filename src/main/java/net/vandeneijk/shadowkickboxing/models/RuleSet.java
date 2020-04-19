/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class RuleSet {

    @Id
    private Integer ruleSetId;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "ruleSetForFight")
    private Collection<Fight> fights;

    @OneToMany(mappedBy = "ruleSetForCombo")
    private Collection<Combo> combos;

    protected RuleSet() {}

    public RuleSet(Integer ruleSetId, String description) {
        this.ruleSetId = ruleSetId;
        this.description = description;
    }

    public Integer getRuleSetId() {
        return ruleSetId;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Fight> getFights() {
        return fights;
    }

    public void setFights(Collection<Fight> fights) {
        this.fights = fights;
    }

    public Collection<Combo> getCombos() {
        return combos;
    }

    public void setCombos(Collection<Combo> combos) {
        this.combos = combos;
    }
}
