package delu.cons.edu.signal;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Andrii_Ostapenko1
 * @created 20/04/2021 - 11:32 AM
 */
public class SignalImpl implements Signal {

    private final AtomicLong timeCreating;

    public SignalImpl() {
        timeCreating = new AtomicLong(System.nanoTime());
    }

    @Override
    public long getTimeCreating() {
        return timeCreating.getAcquire();
    }

    @Override
    public void setTimeCreating(long timeCreation) {
        timeCreating.setRelease(timeCreation);
    }

    @Override
    public void clear() {
        timeCreating.setRelease(0);
    }
}
