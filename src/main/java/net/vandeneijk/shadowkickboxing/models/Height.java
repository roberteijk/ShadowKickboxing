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
public class Height {

    @Id
    private Long heightId;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "height")
    private Collection<OffensiveMove> offensiveMoves;

    protected Height() {}

    public Height(Long heightId, String description) {
        this.heightId = heightId;
        this.description = description;
    }

    public Long getHeightId() {
        return heightId;
    }

    public String getDescription() {
        return description;
    }

    public Collection<OffensiveMove> getOffensiveMoves() {
        return offensiveMoves;
    }

    public void setOffensiveMoves(Collection<OffensiveMove> offensiveMoves) {
        this.offensiveMoves = offensiveMoves;
    }
}
