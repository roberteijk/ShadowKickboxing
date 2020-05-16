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
    @ManyToOne
    private Language language;

    @NotNull
    @ManyToOne
    private Speed speed;

    @NotNull
    @ManyToOne
    private Length length;

    @NotNull
    @Column(columnDefinition = "longblob")
    private byte[] audioFragment;

    protected Fight() {}

    public Fight(@NotNull Language language, @NotNull Speed speed, @NotNull Length length, @NotNull byte[] audioFragment) {
        this.language = language;
        this.speed = speed;
        this.length = length;
        this.audioFragment = audioFragment;
    }

    public Long getFightId() {
        return fightId;
    }

    public Language getLanguage() {
        return language;
    }

    public Speed getSpeed() {
        return speed;
    }

    public Length getLength() {
        return length;
    }

    public byte[] getAudioFragment() {
        return audioFragment;
    }
}
