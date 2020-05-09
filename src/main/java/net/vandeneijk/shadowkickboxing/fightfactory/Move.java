/**
 * Created by Robert van den Eijk on 9-5-2020.
 */

package net.vandeneijk.shadowkickboxing.fightfactory;

import java.util.List;

public class Move {

    private final List<Byte> moveAudio;
    private final int moveAudioLengthMillis;

    public Move(List<Byte> moveAudio, int moveAudioLengthMillis) {
        this.moveAudio = moveAudio;
        this.moveAudioLengthMillis = moveAudioLengthMillis;
    }

    public List<Byte> getMoveAudio() {
        return moveAudio;
    }

    public int getMoveAudioLengthMillis() {
        return moveAudioLengthMillis;
    }
}
