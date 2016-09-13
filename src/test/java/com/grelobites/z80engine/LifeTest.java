package com.grelobites.z80engine;

import com.grelobites.z80engine.components.Oscillator;
import com.grelobites.z80engine.components.Z80;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class LifeTest {

    @Test
    public void bindZ80ToOscillatorAndRun() throws Exception {
        Oscillator oscillator = new Oscillator(1000L);
        Z80 z80 = new Z80();
        oscillator.bindDevice(z80.getPinByName(Z80.CLK_PIN));
        oscillator.start();
        Thread.sleep(10000L);
        oscillator.stop();
        assertNotEquals(0, z80.getElapsedClockPulses());
    }
}
