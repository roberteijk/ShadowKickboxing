/**
 * Created by Robert van den Eijk on 1-6-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Entity
public class Expertise implements InstructionPreFilter {

    @Id
    private Long expertiseId;

    @NotNull
    @Column(unique = true)
    private String description;

    @NotNull
    @Column(unique = true)
    private String descriptionIn2Chars;

    @ManyToMany(mappedBy = "expertiseSet")
    private Collection<Instruction> instructionCollection;

    @OneToMany(mappedBy = "expertise")
    private Collection<Fight> fightCollection;

    protected Expertise() {}

    public Expertise(Long ExpertiseId, @NotNull String description, @NotNull String descriptionIn2Chars) {
        this.expertiseId = ExpertiseId;
        this.description = description;
        this.descriptionIn2Chars = descriptionIn2Chars;
    }

    public Long getExpertiseId() {
        return expertiseId;
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
        instructions.removeIf(x -> !x.getExpertiseSet().contains(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expertise expertise = (Expertise) o;

        return descriptionIn2Chars.equals(expertise.descriptionIn2Chars);
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
