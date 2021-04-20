package delu.cons.edu.signal;

import delu.cons.edu.AbstractCancellableRunnable;
import delu.cons.edu.filter.Filter;

public class SignalHandlerTask extends AbstractCancellableRunnable {

    private final Filter signalFilter;

    private final SignalSource signalSource;

    public SignalHandlerTask(Filter signalFilter, SignalSource signalSource) {
        this.signalFilter = signalFilter;
        this.signalSource = signalSource;
    }

    @Override
    public void run() {
        super.run();
        long reject = 0;
        while (!isCancelled()) {
            Signal signal = waitForExternalSignal();
            if (signalFilter.isAllowed(signal)) {
                if (reject != 0) {
                    System.out.println(Thread.currentThread().getName() + " - Rejects signals = " + reject);
                    reject = 0;
                }
                process(signal);
            } else {
                reject++;
            }
        }
    }

    private Signal waitForExternalSignal() {
        return signalSource.waitForExternalSignal();
    }

    private void process(Signal signal) {
        signal.clear();
    }
}
