package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.PrivateMessage;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.PublicMessage;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.StructureView;

import java.util.Comparator;
import java.util.List;

public class MessagesReceiver {
    public void history(List<PrivateMessage> messages) {
        messages.sort(Comparator.comparing(PrivateMessage::getDate));

        User1 target = StructureView.targetUser.get();
        User1 auth = StructureView.authUser.get();
        EventSender.sendMessage("\nИстория сообщений с " + target.getLogin() + ":");

        for (PrivateMessage message : messages) {
            if (message.getParent().equals(auth)) {
                EventSender.sendSelfMessage(auth.getLogin() + " [" +
                        StructureView.formatDateTime.format(message.getDate()) + "]: " + message.getMessage());
            }
            else {
                EventSender.sendMessage(target.getLogin() + " [" +
                        StructureView.formatDateTime.format(message.getDate()) + "]: " + message.getMessage());
            }
        }

        EventSender.sendMessage("Конец истории сообщений с " + target.getLogin() + ":\n");
    }

    public void publish(String message) {
        EventSender.sendMessage("Сообщение \"" + message + "\" опубликовано");
    }

    public void historyPublish(List<PublicMessage> messages, User1 target) {
        messages.sort(Comparator.comparing(PublicMessage::getDate));

        EventSender.sendMessage("\nИстория публикаций " + target.getLogin() + ":");

        for (PublicMessage message : messages) {
            EventSender.sendMessage(target.getLogin() + " [" +
                    StructureView.formatDateTime.format(message.getDate()) + "]: " + message.getMessage());
        }

        EventSender.sendMessage("Конец истории публикаций " + target.getLogin() + ":\n");
    }
}
