package delu.cons.edu;

public interface CancellableRunnable extends Runnable {
    boolean isCancelled();

    void cancel();
}
