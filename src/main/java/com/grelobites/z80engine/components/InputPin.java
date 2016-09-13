package com.grelobites.z80engine.components;

import com.grelobites.z80engine.iface.Pin;
import com.grelobites.z80engine.iface.PinListener;
import com.grelobites.z80engine.iface.PinStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class InputPin implements Pin {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputPin.class);

    private PinStatus status = PinStatus.UNDEFINED;
    private Set<PinListener> listeners = new HashSet<>();

    @Override
    public boolean isInput() {
        return true;
    }

    @Override
    public boolean isOutput() {
        return false;
    }

    @Override
    public boolean isTriState() {
        return false;
    }

    @Override
    public void setStatus(PinStatus newStatus) {
        if (newStatus != status) {
            listeners.forEach(l -> l.onPinStateChange(this.status, newStatus));
            this.status = newStatus;
        }
    }

    @Override
    public PinStatus getStatus() {
        return status;
    }

    public void addListener(PinListener listener) {
        listeners.add(listener);
    }

    public void removeListener(PinListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void connect(Pin pin) {
        throw new IllegalStateException("Input pin cannot be connected");
    }

    @Override
    public void disconnect(Pin pin) {
        throw new IllegalStateException("Input pin cannot be connected");
    }
}
