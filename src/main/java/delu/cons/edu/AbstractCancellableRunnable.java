package delu.cons.edu;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractCancellableRunnable implements CancellableRunnable {

    protected AtomicBoolean cancelled = new AtomicBoolean(false);

    @Override
    public void run() {
        System.out.println("Start Thread " + Thread.currentThread().getName());
    }

    @Override
    public boolean isCancelled() {
        return cancelled.get() || Thread.currentThread().isInterrupted();
    }

    @Override
    public void cancel() {
        cancelled.set(true);
    }
}
