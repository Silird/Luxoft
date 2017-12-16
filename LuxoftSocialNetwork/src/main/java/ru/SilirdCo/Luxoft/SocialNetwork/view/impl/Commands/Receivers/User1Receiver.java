package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Network;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;

import java.util.List;

public class User1Receiver {
    public User1Receiver() {

    }

    public void createdUser(User1 user1) {
        EventSender.sendMessage("Прользователь \"" + user1.getLogin() + "[" + user1.getId() + "]\" успешно создан");
    }

    public void showUsers(List<User1> user1s) {
        StringBuilder str = new StringBuilder("Пользователи: ");
        boolean first = true;
        for (User1 user1 : user1s) {
            if (first) {
                first = false;
            }
            else {
                str.append(", ");
            }

            str.append(user1.getLogin());

            str.append("[");
            str.append(user1.getId());
            str.append("]");
        }
        EventSender.sendMessage(str.toString());
    }
}
