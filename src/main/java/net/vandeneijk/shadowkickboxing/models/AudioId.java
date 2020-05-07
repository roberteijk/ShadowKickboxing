/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import java.io.Serializable;

public class AudioId implements Serializable {

    private Long instruction;
    private Long language;

    protected AudioId() {}

    public AudioId(Long instruction, Long language) {
        this.instruction = instruction;
        this.language = language;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AudioId audioId = (AudioId) o;

        if (!instruction.equals(audioId.instruction)) return false;
        return language.equals(audioId.language);
    }

    @Override
    public int hashCode() {
        int result = instruction.hashCode();
        result = 31 * result + language.hashCode();
        return result;
    }
}
