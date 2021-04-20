package delu.cons.edu;

import delu.cons.edu.filter.SignalFilter;
import delu.cons.edu.signal.Signal;
import delu.cons.edu.signal.SignalHandlerTask;
import delu.cons.edu.signal.SignalImpl;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class AppMock {

    public static void main(String[] args) {
        SignalFilter signalFilter = new SignalFilter(100, TimeUnit.SECONDS.toNanos(1));

        BlockingDeque<Signal> inputSignals = new LinkedBlockingDeque<>();
        SignalSourceMock signalSourceMock = new SignalSourceMock(1, inputSignals);

        SignalMockGeneratorTask<Signal> signalSignalMockGeneratorTask
                = new SignalMockGeneratorTask<>(400_000_000, inputSignals, SignalImpl::new, 25);
        Thread signalMockGeneratorTaskTread = new Thread(signalSignalMockGeneratorTask, "DSP - Signal Mock Generator");
        signalMockGeneratorTaskTread.start();

        int signalHandlerThreads = 1;
        for (int i = 0; i < signalHandlerThreads; i++) {
            SignalHandlerTask signalHandlerTask = new SignalHandlerTask(signalFilter, signalSourceMock);
            Thread signalHandlerTaskThread = new Thread(signalHandlerTask, "DSP " + i + "- Signal Handler");
            signalHandlerTaskThread.start();
        }
    }
}
