/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class OffensiveMove {

    @Id
    private Long offensiveMoveId;

    @NotNull
    private String description;

    @ManyToOne
    @NotNull
    private BodyPart bodyPartForOffensiveMove;

    @ManyToOne
    @NotNull
    private Height height;

    @NotNull
    private Integer minExecutionMillis;

    @OneToMany(mappedBy = "offensiveMove")
    private Collection<Audio> audioCollection;

    @OneToMany(mappedBy = "offensiveMoveForComboOffensiveMove")
    private Collection<ComboOffensiveMove> comboOffensiveMoves;

    protected OffensiveMove() {}

    public OffensiveMove(Long offensiveMoveId, String description, BodyPart bodyPartForOffensiveMove, Height height, Integer minExecutionMillis) {
        this.offensiveMoveId = offensiveMoveId;
        this.description = description;
        this.bodyPartForOffensiveMove = bodyPartForOffensiveMove;
        this.height = height;
        this.minExecutionMillis = minExecutionMillis;
    }

    public Long getOffensiveMoveId() {
        return offensiveMoveId;
    }

    public String getDescription() {
        return description;
    }

    public BodyPart getBodyPartForOffensiveMove() {
        return bodyPartForOffensiveMove;
    }

    public Height getHeight() {
        return height;
    }

    public Integer getMinExecutionMillis() {
        return minExecutionMillis;
    }

    public Collection<Audio> getAudioCollection() {
        return audioCollection;
    }

    public void setAudioCollection(Collection<Audio> audioCollection) {
        this.audioCollection = audioCollection;
    }

    public Collection<ComboOffensiveMove> getComboOffensiveMoves() {
        return comboOffensiveMoves;
    }

    public void setComboOffensiveMoves(Collection<ComboOffensiveMove> comboOffensiveMoves) {
        this.comboOffensiveMoves = comboOffensiveMoves;
    }
}
