/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Fight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fightId;

    @ManyToOne
    @NotNull
    private RuleSet ruleSetForFight;

    @NotNull
    private Integer numberOfRounds;

    @NotNull
    private Integer roundLengthMillis;

    @NotNull
    private Integer restLengthMillis;

    @NotNull
    private byte[] data;

    protected Fight() {}

    public Fight(RuleSet ruleSetForFight, Integer numberOfRounds, Integer roundLengthMillis, Integer restLengthMillis, byte[] data) {
        this.ruleSetForFight = ruleSetForFight;
        this.numberOfRounds = numberOfRounds;
        this.roundLengthMillis = roundLengthMillis;
        this.restLengthMillis = restLengthMillis;
        this.data = data;
    }

    public Long getFightId() {
        return fightId;
    }

    public RuleSet getRuleSetForFight() {
        return ruleSetForFight;
    }

    public Integer getNumberOfRounds() {
        return numberOfRounds;
    }

    public Integer getRoundLengthMillis() {
        return roundLengthMillis;
    }

    public Integer getRestLengthMillis() {
        return restLengthMillis;
    }

    public byte[] getData() {
        return data;
    }
}
