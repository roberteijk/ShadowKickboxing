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

    @NotNull
    private Boolean allowUpperBody;

    @NotNull
    private Boolean allowLowerBody;

    @ManyToMany(mappedBy = "expertiseSet")
    private Collection<Instruction> instructionCollection;

    @OneToMany(mappedBy = "expertise")
    private Collection<Fight> fightCollection;

    protected Expertise() {}

    public Expertise(Long ExpertiseId, @NotNull String description, @NotNull String descriptionIn2Chars, @NotNull Boolean allowUpperBody, @NotNull Boolean allowLowerBody) {
        this.expertiseId = ExpertiseId;
        this.description = description;
        this.descriptionIn2Chars = descriptionIn2Chars;
        this.allowUpperBody = allowUpperBody;
        this.allowLowerBody = allowLowerBody;
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

    public Boolean isAllowUpperBody() {
        return allowUpperBody;
    }

    public Boolean isAllowLowerBody() {
        return allowLowerBody;
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
    public String toString() {
        return descriptionIn2Chars;
    }
}
