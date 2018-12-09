package com.github.dawidd6.andttt.events;

public class ConnectSuccessEvent {
    private String address;

    public ConnectSuccessEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
