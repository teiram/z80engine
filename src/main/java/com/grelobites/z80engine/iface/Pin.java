package com.grelobites.z80engine.iface;

public interface Pin {
    boolean isInput();
    boolean isOutput();
    boolean isTriState();

    public void setStatus(PinStatus status);
    public PinStatus getStatus();
    public void connect(Pin pin);
    public void disconnect(Pin pin);
}
