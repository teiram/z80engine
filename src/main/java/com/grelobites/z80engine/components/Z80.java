package com.grelobites.z80engine.components;

import com.grelobites.z80engine.iface.Device;
import com.grelobites.z80engine.iface.Pin;
import com.grelobites.z80engine.iface.PinStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Z80 implements Device {
    private static final Logger LOGGER = LoggerFactory.getLogger(Z80.class);
    public static final String CLK_PIN = "CLK";

    private long elapsedClockPulses = 0;
    private Map<String, Pin> devicePins = new HashMap<>();

    @Override
    public String getId() {
        return null;
    }

    private void initializePins() {
        InputPin clk = new InputPin();
        clk.addListener((oldStatus, newStatus) -> clkPulse(newStatus));
        devicePins.put(CLK_PIN, clk);
    }

    public Z80() {
        initializePins();
    }

    public void clkPulse(PinStatus status) {
        elapsedClockPulses++;
    }

    @Override
    public Pin getPinByName(String name) {
        Pin pin = devicePins.get(name);
        if (pin != null) {
            return pin;
        } else {
            throw new IllegalArgumentException("No PIN under name " + name);
        }
    }

    public long getElapsedClockPulses() {
        return elapsedClockPulses;
    }
}
