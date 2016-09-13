package com.grelobites.z80engine.components;


import com.grelobites.z80engine.iface.ClockSource;
import com.grelobites.z80engine.iface.Pin;
import com.grelobites.z80engine.iface.PinStatus;
import com.grelobites.z80engine.iface.RunningStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class Oscillator implements ClockSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(Oscillator.class);

    private Thread oscillatorThread;
    private Long frequency;
    private long cycleCounter;
    private RunningStatus runningStatus = RunningStatus.STOPPED;

    private Set<Pin> boundedPins = new HashSet<>();

    public Oscillator(Long frequency) {
        this.frequency = frequency;
    }

    public void bindDevice(Pin pin) {
        boundedPins.add(pin);
    }

    public void unbindDevice(Pin pin) {
        boundedPins.remove(pin);
    }

    private static void sleepNanos(long delayNanos) {
        if (delayNanos < 0) {
            LOGGER.debug("Oscillator is not able to keep up with the frequency. Negative delay " + delayNanos);
        } else {
            try {
                Thread.sleep(delayNanos / 1000000, (int) delayNanos % 1000000);
            } catch (InterruptedException ie) {
                LOGGER.debug("Oscillator thread was interrupted");
            }
        }
    }

    private void oscillatorService() {
        runningStatus = RunningStatus.RUNNING;
        cycleCounter = 0;
        double halfCycleDelay = 1000000000.0 / (frequency * 2.0); //Nanoseconds delay
        LOGGER.debug("Half cycle delay calculated as " + halfCycleDelay + " ns");
        while (runningStatus == RunningStatus.RUNNING) {
            long halfCycleMark = System.nanoTime();
            //Set pins high
            boundedPins.forEach(p -> p.setStatus(PinStatus.HIGH));
            long elapsed = System.nanoTime() - halfCycleMark;
            Double delay = halfCycleDelay - elapsed; //Half clock cycle minus elapsed time
            sleepNanos(delay.longValue());
            halfCycleMark = System.nanoTime();
            //Set pins low
            boundedPins.forEach(p -> p.setStatus(PinStatus.LOW));
            elapsed = System.nanoTime() - halfCycleMark;
            delay = halfCycleDelay - elapsed;
            sleepNanos(delay.longValue());
            cycleCounter++;
        }
        runningStatus = RunningStatus.STOPPED;
    }

    public void start() {
        if (runningStatus == RunningStatus.STOPPED) {
            runningStatus = RunningStatus.STARTING;
            oscillatorThread = new Thread(() -> oscillatorService());
            oscillatorThread.start();
        }
    }

    public void stop() {
        if (runningStatus == RunningStatus.RUNNING) {
            runningStatus = RunningStatus.STOPPING;
            do {
                try {
                    oscillatorThread.join();
                } catch (InterruptedException ie) {
                }
            } while (runningStatus != RunningStatus.STOPPED);
        }
    }

    public long getCycleCounter() {
        return cycleCounter;
    }
}
