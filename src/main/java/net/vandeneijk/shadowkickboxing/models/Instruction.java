/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Instruction {

    @Id
    private Long instructionId;

    @NotNull
    private String description;

    @NotNull
    private Boolean move;

    @NotNull
    private Double callFrequencyWeight;

    @NotNull
    private Integer minExecutionTimeMillis;

    @NotNull
    private Integer maxExecutionTimeMillis;

    @OneToMany(mappedBy = "instruction")
    private Collection<Audio> audioCollection;

    protected Instruction() {}

    public Instruction(Long instructionId, @NotNull String description, @NotNull Boolean move) {
        this(instructionId, description, move, 0.0, 0, 0);
    }

    public Instruction(Long instructionId, @NotNull String description, @NotNull Boolean move, @NotNull Double callFrequencyWeight, @NotNull Integer minExecutionTimeMillis, @NotNull Integer maxExecutionTimeMillis) {
        if (callFrequencyWeight < 0.01 || callFrequencyWeight > 1.0) throw new IllegalArgumentException("callFrequencyWeight should have a value between 0.01 and 1.0.");
        if (minExecutionTimeMillis < 0 || minExecutionTimeMillis > 10000) throw new IllegalArgumentException("minExecutionTimeMillis should have a value between 0 and 10000.");
        if (maxExecutionTimeMillis < 0 || maxExecutionTimeMillis > 10000) throw new IllegalArgumentException("maxExecutionTimeMillis should have a value between 0 and 10000.");
        this.instructionId = instructionId;
        this.description = description;
        this.move = move;
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