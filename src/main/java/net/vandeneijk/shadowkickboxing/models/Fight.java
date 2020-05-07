/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Fight {

    @Id
    private Long fightId;

    @NotNull
    @Column(columnDefinition = "mediumblob")
    private byte[] audioFragment;

    protected Fight() {}

    public Fight(Long fightId, @NotNull byte[] audioFragment) {
        this.fightId = fightId;
        this.audioFragment = audioFragment;
    }

    public Long getFightId() {
        return fightId;
    }

    public byte[] getAudioFragment() {
        return audioFragment;
    }
}
