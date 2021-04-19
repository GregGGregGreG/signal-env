package delu.cons.edu;

import delu.cons.edu.filter.FilterTimerTask;
import delu.cons.edu.filter.SignalFilter;
import delu.cons.edu.signal.Signal;
import delu.cons.edu.signal.SignalHandlerTask;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class AppMock {

    public static void main(String[] args) {
        SignalFilter signalFilter = new SignalFilter(1000_000_000, 1000_000_000);

        FilterTimerTask signalFilterTask = new FilterTimerTask(signalFilter);
        Thread signalFilterTaskThread = new Thread(signalFilterTask, "DSP - Signal Filter Timer");
        signalFilterTaskThread.start();

        BlockingDeque<Signal> inputSignals = new LinkedBlockingDeque<>();
        SignalSourceMock signalSourceMock = new SignalSourceMock(1, inputSignals);

        SignalMockGeneratorTask<Signal> signalSignalMockGeneratorTask
                = new SignalMockGeneratorTask<>( 20_000, inputSignals, () -> new Signal() {}, 500);
        Thread signalMockGeneratorTaskTread = new Thread(signalSignalMockGeneratorTask, "DSP - Signal Mock Generator");
        signalMockGeneratorTaskTread.start();

        SignalHandlerTask signalHandlerTask = new SignalHandlerTask(signalFilter, signalSourceMock);
        Thread signalHandlerTaskThread = new Thread(signalHandlerTask, "DSP - Signal Handler");
        signalHandlerTaskThread.start();
    }
}
