package com.grelobites.z80engine.iface;


public interface Device extends Node {

    Pin getPinByName(String name);
}
