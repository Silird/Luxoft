package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events;

import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.Factories.SenderFactory;

public class EventSender {
    public static void sendMessage(String text) {
        SenderFactory.getInstance()
                .getEvents()
                .send(new Event(EventType.MESSAGE, text));
    }

    public static void sendCommand(String text) {
        SenderFactory.getInstance()
                .getEvents()
                .send(new Event(EventType.COMMAND, text));
    }
}
