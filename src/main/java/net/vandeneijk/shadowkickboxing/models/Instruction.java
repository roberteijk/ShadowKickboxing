/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long instructionId;

    @NotNull
    @Column(unique = true)
    private String description;

    @NotNull
    private Boolean move;

    @ManyToMany
    private Set<Expertise> expertiseSet = new HashSet<>();

    @ManyToMany
    private Set<DefensiveMode> defensiveModeSet = new HashSet<>();

//    @NotNull
//    private Boolean useUpperBody;
//
//    @NotNull
//    private Boolean useLowerBody;
//
//    @NotNull
//    private Boolean canBlock;
//
//    @NotNull
//    private Boolean canEvade;

    @NotNull
    private Double callFrequencyWeight;

    @NotNull
    private Integer minExecutionTimeMillis;

    @NotNull
    private Integer maxExecutionTimeMillis;

    @OneToMany(mappedBy = "instruction")
    private Collection<Audio> audioCollection;

    protected Instruction() {}

    public Instruction(@NotNull String description, @NotNull Boolean move) {
        this(description, move, /*false, false, false, false,*/ 1.0, 0, 0);
    }

    public Instruction(@NotNull String description, @NotNull Boolean move, /*@NotNull Boolean useUpperBody, @NotNull Boolean useLowerBody, @NotNull Boolean canBlock, @NotNull Boolean canEvade,*/ @NotNull Double callFrequencyWeight, @NotNull Integer minExecutionTimeMillis, @NotNull Integer maxExecutionTimeMillis) {
        if (callFrequencyWeight < 0.001 || callFrequencyWeight > 1.0) throw new IllegalArgumentException("callFrequencyWeight should have a value between 0.001 and 1.0.");
        if (minExecutionTimeMillis < 0 || minExecutionTimeMillis > 10000) throw new IllegalArgumentException("minExecutionTimeMillis should have a value between 0 and 10000.");
        if (maxExecutionTimeMillis < 0 || maxExecutionTimeMillis > 10000) throw new IllegalArgumentException("maxExecutionTimeMillis should have a value between 0 and 10000.");
        this.description = description;
        this.move = move;
//        this.useUpperBody = useUpperBody;
//        this.useLowerBody = useLowerBody;
//        this.canBlock = canBlock;
//        this.canEvade = canEvade;
        this.callFrequencyWeight = callFrequencyWeight;
        this.minExecutionTimeMillis = minExecutionTimeMillis;
        this.maxExecutionTimeMillis = maxExecutionTimeMillis;
    }

    public Long getInstructionId() {
        return instructionId;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isMove() {
        return move;
    }

    public Set<Expertise> getExpertiseSet() {
        return expertiseSet;
    }

    public void addExpertiseToCollection(Expertise expertise) {
        expertiseSet.add(expertise);
    }

    public void setExpertiseSet(Set<Expertise> expertiseSet) {
        this.expertiseSet = expertiseSet;
    }

    public Set<DefensiveMode> getDefensiveModeSet() {
        return defensiveModeSet;
    }

    public void addDefensiveModeToCCollection(DefensiveMode defensiveMode) {
        defensiveModeSet.add(defensiveMode);
    }

    public void setDefensiveModeSet(Set<DefensiveMode> defensiveModeSet) {
        this.defensiveModeSet = defensiveModeSet;
    }

//    public Boolean isUseUpperBody() {
//        return useUpperBody;
//    }
//
//    public Boolean isUseLowerBody() {
//        return useLowerBody;
//    }
//
//    public Boolean isCanBlock() {
//        return canBlock;
//    }
//
//    public Boolean isCanEvade() {
//        return canEvade;
//    }

    public Double getCallFrequencyWeight() {
        return callFrequencyWeight;
    }

    public Integer getMinExecutionTimeMillis() {
        return minExecutionTimeMillis;
    }

    public Integer getMaxExecutionTimeMillis() {
        return maxExecutionTimeMillis;
    }

    public Collection<Audio> getAudioCollection() {
        return audioCollection;
    }

    public void setAudioCollection(Collection<Audio> audioCollection) {
        this.audioCollection = audioCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instruction that = (Instruction) o;

        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public String toString() {
        return description + "   " + expertiseSet + "  " + defensiveModeSet;
    }
}