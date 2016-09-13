package com.grelobites.z80engine.iface;


public interface ClockSource {
    void bindDevice(Pin pin);
    void unbindDevice(Pin pin);
}
