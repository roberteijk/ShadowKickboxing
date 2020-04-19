/**
 * Created by Robert van den Eijk on 19-4-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import java.io.Serializable;

public class AudioId implements Serializable {

    private OffensiveMove offensiveMove;
    private Language language;

    protected AudioId() {}

    public AudioId(OffensiveMove offensiveMove, Language language) {
        this.offensiveMove = offensiveMove;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AudioId audioId = (AudioId) o;

        if (!offensiveMove.equals(audioId.offensiveMove)) return false;
        return language.equals(audioId.language);
    }

    @Override
    public int hashCode() {
        int result = offensiveMove.hashCode();
        result = 31 * result + language.hashCode();
        return result;
    }
}
