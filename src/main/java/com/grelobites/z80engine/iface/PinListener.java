package com.grelobites.z80engine.iface;

@FunctionalInterface
public interface PinListener {

    void onPinStateChange(PinStatus oldState, PinStatus newState);
}
