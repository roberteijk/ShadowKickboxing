/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Fight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fightId;

    @NotNull
    private Integer numberOfRounds;

    @NotNull
    @ManyToOne
    private Language language;

    @NotNull
    @ManyToOne
    private Speed speed;

    @NotNull
    @Column(columnDefinition = "mediumblob")
    private byte[] audioFragment;

    protected Fight() {}

    public Fight(@NotNull Integer numberOfRounds, @NotNull Language language, @NotNull Speed speed, @NotNull byte[] audioFragment) {
        this.numberOfRounds = numberOfRounds;
        this.language = language;
        this.speed = speed;
        this.audioFragment = audioFragment;
    }

    public Long getFightId() {
        return fightId;
    }

    public Integer getNumberOfRounds() {
        return numberOfRounds;
    }

    public Language getLanguage() {
        return language;
    }

    public Speed getSpeed() {
        return speed;
    }

    public byte[] getAudioFragment() {
        return audioFragment;
    }
}
