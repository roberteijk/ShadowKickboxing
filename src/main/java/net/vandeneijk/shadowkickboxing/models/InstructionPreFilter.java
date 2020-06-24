/**
 * Created by Robert van den Eijk on 18-6-2020.
 */

package net.vandeneijk.shadowkickboxing.models;

import java.util.Set;

public interface InstructionPreFilter {

    void filter(Set<Instruction> instructions);
}
