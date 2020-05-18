/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

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

    @NotNull
    private Boolean canBlock;

    @NotNull
    private Boolean canEvade;

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
        this(description, move, false, false, 1.0, 0, 0);
    }

    public Instruction(@NotNull String description, @NotNull Boolean move, @NotNull Boolean canBlock, @NotNull Boolean canEvade, @NotNull Double callFrequencyWeight, @NotNull Integer minExecutionTimeMillis, @NotNull Integer maxExecutionTimeMillis) {
        if (callFrequencyWeight < 0.01 || callFrequencyWeight > 1.0) throw new IllegalArgumentException("callFrequencyWeight should have a value between 0.01 and 1.0.");
        if (minExecutionTimeMillis < 0 || minExecutionTimeMillis > 10000) throw new IllegalArgumentException("minExecutionTimeMillis should have a value between 0 and 10000.");
        if (maxExecutionTimeMillis < 0 || maxExecutionTimeMillis > 10000) throw new IllegalArgumentException("maxExecutionTimeMillis should have a value between 0 and 10000.");
        this.description = description;
        this.move = move;
        this.canBlock = canBlock;
        this.canEvade = canEvade;
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

    public Boolean isCanBlock() {
        return canBlock;
    }

    public Boolean isCanEvade() {
        return canEvade;
    }

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
}