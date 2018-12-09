package com.github.dawidd6.andttt.events;

public class ConnectEvent {
    private String host;
    private int port;

    public ConnectEvent(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
