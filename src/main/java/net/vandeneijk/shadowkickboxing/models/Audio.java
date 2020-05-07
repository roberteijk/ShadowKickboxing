/**
 * Created by Robert van den Eijk on 7-5-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(AudioId.class)
public class Audio {

    @Id
    @ManyToOne
    private Instruction instruction;

    @Id
    @ManyToOne
    private Language language;

    @NotNull
    private Integer lengthMillis;

    @NotNull
    @Column(columnDefinition = "blob")
    private byte[] audioFragment;

    protected Audio() {}

    public Audio(Instruction instruction, Language language, @NotNull Integer lengthMillis, @NotNull byte[] audioFragment) {
        this.instruction = instruction;
        this.language = language;
        this.lengthMillis = lengthMillis;
        this.audioFragment = audioFragment;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public Language getLanguage() {
        return language;
    }

    public Integer getLengthMillis() {
        return lengthMillis;
    }

    public byte[] getAudioFragment() {
        return audioFragment;
    }
}
