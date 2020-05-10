/**
 * Created by Robert van den Eijk on 9-5-2020.
 */

package net.vandeneijk.shadowkickboxing.fightfactory;

import net.vandeneijk.shadowkickboxing.models.Instruction;

import java.util.List;

public class Move {

    private List<Byte> bytesListAudioMove;
    private int totalMoveAudioLengthMillis;
    private Instruction originalInstruction;

    public Move(List<Byte> bytesListAudioMove, int totalMoveAudioLengthMillis, Instruction originalInstruction) {
        this.bytesListAudioMove = bytesListAudioMove;
        this.totalMoveAudioLengthMillis = totalMoveAudioLengthMillis;
        this.originalInstruction = originalInstruction;
    }

    public List<Byte> getBytesListAudioMove() {
        return bytesListAudioMove;
    }

    public void setBytesListAudioMove(List<Byte> bytesListAudioMove) {
        this.bytesListAudioMove = bytesListAudioMove;
    }

    public int getTotalMoveAudioLengthMillis() {
        return totalMoveAudioLengthMillis;
    }

    public void setTotalMoveAudioLengthMillis(int totalMoveAudioLengthMillis) {
        this.totalMoveAudioLengthMillis = totalMoveAudioLengthMillis;
    }

    public Instruction getOriginalInstruction() {
        return originalInstruction;
    }
}
