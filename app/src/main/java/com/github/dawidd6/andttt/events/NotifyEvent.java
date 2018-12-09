package com.github.dawidd6.andttt.events;

import com.github.dawidd6.andttt.proto.Response;
import com.google.protobuf.InvalidProtocolBufferException;

public class NotifyEvent {
    private String title;
    private String text;
    private boolean persistent;

    public NotifyEvent(String title, String text, boolean persistent) {
        this.title = title;
        this.text = text;
        this.persistent = persistent;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public boolean isPersistent() {
        return persistent;
    }
}
