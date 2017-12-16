package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers;

import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.Sender;

public class CommonReceiver {
    public void sendMessage(String message) {
        EventSender.sendMessage("[INFO]:\n" + message);
    }

    public void sendWarn(String message) {
        EventSender.sendMessage("[WARN]:\n" + message);
    }

    public void sendError(String message) {
        EventSender.sendMessage("[ERROR]:\n" + message);
    }

    public void commandTemplate(String template) {
        sendWarn("Использование команды: (команда) " + template);
    }
}
