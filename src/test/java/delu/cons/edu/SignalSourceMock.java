package delu.cons.edu;

import delu.cons.edu.signal.Signal;
import delu.cons.edu.signal.SignalSource;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Andrii_Ostapenko1
 * @created 19/04/2021 - 1:55 PM
 */
public class SignalSourceMock implements SignalSource {

    public final int bound;
    private final BlockingDeque<Signal> inputSignals;

    public SignalSourceMock(int bound, BlockingDeque<Signal> inputSignals) {
        this.bound = bound;
        this.inputSignals = inputSignals;
    }

    @Override
    public Signal waitForExternalSignal() {
        Signal src = null;
        try {
            src = inputSignals.takeFirst();
        } catch (InterruptedException e) {
            // lets continue
        }
        return src;
    }
}
