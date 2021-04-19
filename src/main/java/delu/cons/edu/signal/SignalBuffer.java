package delu.cons.edu.signal;

/**
 * @author Andrii_Ostapenko1
 * @created 19/04/2021 - 1:39 PM
 */
public interface SignalBuffer {

    Signal poll();

    Signal push();
}
