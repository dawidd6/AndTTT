package com.github.dawidd6.andttt.events;

import com.github.dawidd6.andttt.proto.Request;

public class SendEvent {
    private Request request;

    public SendEvent(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
