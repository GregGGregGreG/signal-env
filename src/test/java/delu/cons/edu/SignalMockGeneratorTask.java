package delu.cons.edu;

import delu.cons.edu.AbstractCancellableRunnable;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Andrii_Ostapenko1
 * @created 19/04/2021 - 3:05 PM
 */
public class SignalMockGeneratorTask<T> extends AbstractCancellableRunnable {

    private final long periodNan;
    private final BlockingDeque<T> inputSignals;
    private final Supplier<T> supplier;
    private final int samplingFreq;

    public SignalMockGeneratorTask(long periodNan, BlockingDeque<T> inputSignals, Supplier<T> supplier, int samplingFreq) {
        this.periodNan = periodNan;
        this.inputSignals = inputSignals;
        this.supplier = supplier;
        this.samplingFreq = samplingFreq;
    }

    @Override
    public void run() {
        super.run();
        long cycleDuration;
        long cycleStart = System.nanoTime();
        while (!isCancelled()) {
            cycleDuration = System.nanoTime() - cycleStart;
            for (int i = 0; i < samplingFreq; i++) {
                try {
                    inputSignals.put(supplier.get());
                } catch (InterruptedException e) {
                   // lest continue
                }
            }
            if (cycleDuration < periodNan) {
                try {
                    TimeUnit.NANOSECONDS.sleep(periodNan - cycleDuration);
                } catch (InterruptedException e) {
                    // lets continue
                }
            }
            cycleStart = cycleStart + periodNan;
        }
    }
}
