package ru.SilirdCo.Luxoft.impl.Messages;

public class Event {
    public static int MESSAGE = 1;
    public static int COMMAND = 2;

    public Event() {
        this(0, null);
    }

    public Event(int type, String text) {
        this.type = type;
        this.text = text;
    }

    private int type;
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
