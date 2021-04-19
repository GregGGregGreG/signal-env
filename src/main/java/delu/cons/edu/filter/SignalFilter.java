package delu.cons.edu.filter;

import java.util.concurrent.atomic.LongAdder;

public class SignalFilter implements Filter {

    private final long threshold;
    private final LongAdder signalCounter = new LongAdder();
    private final long periodNan;

    public SignalFilter(long threshold, long periodNan) {
        this.threshold = threshold;
        this.periodNan = periodNan;
    }

    @Override
    public boolean isAllowed() {
        signalCounter.increment();
        return signalCounter.sum() < threshold;
    }

    @Override
    public void reset() {
        long count = signalCounter.sumThenReset();
        System.out.println("Count process signal " + count);
    }

    @Override
    public long periodNan() {
        return periodNan;
    }
}
