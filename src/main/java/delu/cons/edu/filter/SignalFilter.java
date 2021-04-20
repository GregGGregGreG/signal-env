package delu.cons.edu.filter;

import delu.cons.edu.signal.Signal;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.locks.ReentrantLock;

public class SignalFilter implements Filter {

    private final long timeOffset;
    private final AtomicLongArray timestamps;
    private final AtomicInteger cursor = new AtomicInteger();
    private final int threshold;
    private final ReentrantLock lock = new ReentrantLock();

    public SignalFilter(int threshold, long timeOffset) {
        this.timestamps = new AtomicLongArray(threshold);
        this.threshold = threshold;
        this.timeOffset = timeOffset;
    }

    @Override
    public boolean isAllowed(Signal signal) {
        long timeCreation = signal.getTimeCreating();
        lock.lock();  // block until condition holds
        try {
            boolean isAllowed = timeCreation - timeOffset >= timestamps.getAcquire(cursor.getAcquire());
            if (isAllowed) {
                timestamps.setRelease(cursor.getAndIncrement(), timeCreation);
                cursor.compareAndSet(threshold, 0);
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }

}
