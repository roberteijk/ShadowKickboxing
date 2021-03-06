/**
 * Created by Robert van den Eijk on 27-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Entity
public class DefensiveMode implements InstructionPreFilter {

    @Id
    private Long defensiveModeId;

    @NotNull
    @Column(unique = true)
    private String description;

    @NotNull
    @Column(unique = true)
    private String descriptionIn2Chars;

    @ManyToMany(mappedBy = "defensiveModeSet")
    private Collection<Instruction> instructionCollection;

    @OneToMany(mappedBy = "defensiveMode")
    private Collection<Fight> fightCollection;

    protected DefensiveMode() {}

    public DefensiveMode(Long defensiveModeId, @NotNull String description, @NotNull String descriptionIn2Chars) {
        this.defensiveModeId = defensiveModeId;
        this.description = description;
        this.descriptionIn2Chars = descriptionIn2Chars;
    }

    public Long getDefensiveModeId() {
        return defensiveModeId;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionIn2Chars() {
        return descriptionIn2Chars;
    }

    public Collection<Instruction> getInstructionCollection() {
        return instructionCollection;
    }

    public void setInstructionCollection(Collection<Instruction> instructionCollection) {
        this.instructionCollection = instructionCollection;
    }

    public void filter(Set<Instruction> instructions) {
        instructions.removeIf(x -> !x.getDefensiveModeSet().contains(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefensiveMode that = (DefensiveMode) o;

        return descriptionIn2Chars.equals(that.descriptionIn2Chars);
    }

    @Override
    public int hashCode() {
        return descriptionIn2Chars.hashCode();
    }

    @Override
    public String toString() {
        return descriptionIn2Chars;
    }
}
