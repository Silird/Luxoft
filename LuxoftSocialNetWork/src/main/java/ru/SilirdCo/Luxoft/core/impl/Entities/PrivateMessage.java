package ru.SilirdCo.Luxoft.core.impl.Entities;

public class PrivateMessage extends Message {
    private User from;
    private User to;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
