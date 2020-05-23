/**
 * Created by Robert van den Eijk on 16-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Length {

    @Id
    private Long lengthId;

    @NotNull
    private String description;

    @NotNull
    private String descriptionIn2Chars;

    @NotNull
    private Integer numberRounds;

    @OneToMany(mappedBy = "length")
    private Collection<Fight> speedCollection;

    protected Length() {}

    public Length(Long lengthId, @NotNull String description, @NotNull String descriptionIn2Chars, @NotNull Integer numberRounds) {
        this.lengthId = lengthId;
        this.description = description;
        this.descriptionIn2Chars = descriptionIn2Chars;
        this.numberRounds = numberRounds;
    }

    public Long getLengthId() {
        return lengthId;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionIn2Chars() {
        return descriptionIn2Chars;
    }

    public Integer getNumberRounds() {
        return numberRounds;
    }
}
