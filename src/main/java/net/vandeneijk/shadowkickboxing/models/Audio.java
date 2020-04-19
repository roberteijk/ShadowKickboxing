/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(AudioId.class)
public class Audio {

    @Id
    @ManyToOne()
    private OffensiveMove offensiveMove;

    @Id
    @ManyToOne
    private Language language;

    @NotNull
    private byte[] audioFragment;

    protected Audio() {}

    public Audio(OffensiveMove offensiveMove, Language language, byte[] audioFragment) {
        this.offensiveMove = offensiveMove;
        this.language = language;
        this.audioFragment = audioFragment;
    }

    public OffensiveMove getOffensiveMove() {
        return offensiveMove;
    }

    public Language getLanguage() {
        return language;
    }

    public byte[] getAudioFragment() {
        return audioFragment;
    }
}
