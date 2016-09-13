package com.grelobites.z80engine;

import com.grelobites.z80engine.components.Oscillator;
import com.grelobites.z80engine.components.Z80;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotEquals;

public class LifeTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LifeTest.class);

    @Test
    public void bindZ80ToOscillatorAndRun() throws Exception {
        Oscillator oscillator = new Oscillator(1000L);
        Z80 z80 = new Z80();
        oscillator.bindDevice(z80.getPinByName(Z80.CLK_PIN));
        oscillator.start();
        Thread.sleep(10000L);
        oscillator.stop();
        LOGGER.debug("Oscilator cycles: " + oscillator.getCycleCounter()
            + ", CPU clock changes: " + z80.getElapsedClockPulses());
        assertNotEquals(0, z80.getElapsedClockPulses());
    }
}
