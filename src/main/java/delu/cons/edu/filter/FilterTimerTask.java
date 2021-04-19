package delu.cons.edu.filter;

import delu.cons.edu.AbstractCancellableRunnable;

import java.util.concurrent.TimeUnit;

/**
 * TimerTask run every CLOCK_TICK ns
 */
public class FilterTimerTask extends AbstractCancellableRunnable {
    private final long periodNan;
    private final Filter filter;

    public FilterTimerTask(Filter filter) {
        this.periodNan = filter.periodNan();
        this.filter = filter;
    }

    @Override
    public void run() {
        super.run();
        long cycleDuration;
        long cycleStart = System.nanoTime();
        while (!isCancelled()) {
            writeLog();
            filter.reset();
            cycleDuration = System.nanoTime() - cycleStart;
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

    private void writeLog() {
        System.out.println(Thread.currentThread().getName() + "is tick ");
    }
}
