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
public class Language {

    @Id
    private Integer languageId;

    @NotNull
    private String description;

    @OneToMany(mappedBy = "language")
    private Collection<Audio> audioCollection;

    protected Language() {}

    public Language(Integer languageId, String description) {
        this.languageId = languageId;
        this.description = description;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Audio> getAudioCollection() {
        return audioCollection;
    }

    public void setAudioCollection(Collection<Audio> audioCollection) {
        this.audioCollection = audioCollection;
    }
}
