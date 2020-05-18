/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Language {

    @Id
    private Long languageId;

    @NotNull
    @Column(unique = true)
    private String description;

    @OneToMany(mappedBy = "language")
    private Collection<Audio> audioCollection;

    @OneToMany(mappedBy = "language")
    private Collection<Fight> fightCollection;

    protected Language() {}

    public Language(Long languageId, String description) {
        this.languageId = languageId;
        this.description = description;
    }

    public Long getLanguageId() {
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
