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
        while (!isCancelled()) {
            Signal signal = waitForExternalSignal();
            if (signalFilter.isAllowed()) {
                process(signal);
            } else {
                // do nothing – signal is rejected
                System.out.println("Reject Signal");
            }
        }
    }

    private Signal waitForExternalSignal() {
        return signalSource.waitForExternalSignal();
    }

    private void process(Signal signal) {

    }
}
