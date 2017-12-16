package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events;

public class Event {
    public Event() {
        this(EventType.OTHER, null);
    }

    public Event(EventType type, String text) {
        this.type = type;
        this.text = text;
    }

    private EventType type;
    private String text;

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
