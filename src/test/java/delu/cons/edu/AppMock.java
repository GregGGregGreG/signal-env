package delu.cons.edu;

import delu.cons.edu.filter.SignalFilter;
import delu.cons.edu.signal.Signal;
import delu.cons.edu.signal.SignalHandlerTask;
import delu.cons.edu.signal.SignalImpl;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class AppMock {

    public static void main(String[] args) {
        SignalFilter signalFilter = new SignalFilter(50_000, 1_000_000_000);

        BlockingDeque<Signal> inputSignals = new LinkedBlockingDeque<>();
        SignalSourceMock signalSourceMock = new SignalSourceMock(1, inputSignals);

        SignalMockGeneratorTask<Signal> signalSignalMockGeneratorTask
                = new SignalMockGeneratorTask<>(2_000_000, inputSignals, SignalImpl::new, 45);
        Thread signalMockGeneratorTaskTread = new Thread(signalSignalMockGeneratorTask, "DSP - Signal Mock Generator");
        signalMockGeneratorTaskTread.start();

        int signalHandlerThreads = 20;
        for (int i = 0; i < signalHandlerThreads; i++) {
            SignalHandlerTask signalHandlerTask = new SignalHandlerTask(signalFilter, signalSourceMock);
            Thread signalHandlerTaskThread = new Thread(signalHandlerTask, "DSP " + i + "- Signal Handler");
            signalHandlerTaskThread.start();
        }
    }
}
