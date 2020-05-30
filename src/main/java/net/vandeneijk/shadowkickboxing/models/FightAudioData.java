/**
 * Created by Robert van den Eijk on 28-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class FightAudioData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fightAudioDataId;

    @NotNull
    @Column(columnDefinition = "longblob")
    private byte[] audioFragment;

    protected FightAudioData() {}

    public FightAudioData(@NotNull byte[] audioFragment) {
        this.audioFragment = audioFragment;
    }

    public Long getFightAudioDataId() {
        return fightAudioDataId;
    }

    public byte[] getAudioFragment() {
        return audioFragment;
    }
}
