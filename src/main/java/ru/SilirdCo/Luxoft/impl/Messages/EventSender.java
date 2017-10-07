package ru.SilirdCo.Luxoft.impl.Messages;

import ru.SilirdCo.Luxoft.impl.Util.Factories.SenderFactory;

public class EventSender {
    public static void sendMessage(String text) {
        SenderFactory.getInstance()
                .getEvents()
                .send(new Event(Event.MESSAGE, text));
    }

    public static void sendCommand(String text) {
        SenderFactory.getInstance()
                .getEvents()
                .send(new Event(Event.COMMAND, text));
    }
}
