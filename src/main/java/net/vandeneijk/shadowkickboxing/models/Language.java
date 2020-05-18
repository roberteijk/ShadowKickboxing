/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long languageId;

    @NotNull
    @Column(unique = true)
    private String description;

    @OneToMany(mappedBy = "language")
    private Collection<Audio> audioCollection;

    @OneToMany(mappedBy = "language")
    private Collection<Fight> fightCollection;

    protected Language() {}

    public Language(String description) {
        this.description = description;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public String getDescription() {
        return description;
    }
}
