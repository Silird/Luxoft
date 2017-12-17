package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.StructureView;

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

    public void authUser(User1 user1) {
        logout();
        StructureView.authUser.setValue(user1);
        EventSender.sendMessage("Авторизация прошла успешна. Текущий пользователь: \"" + user1.getLogin() + "[" + user1.getId() + "]\"");
    }

    public void logout() {
        StructureView.authUser.setValue(null);
        StructureView.targetUser.setValue(null);
        EventSender.sendMessage("Вы вышли из сети");
    }

    public void selectTarget(User1 user1) {
        leave();
        StructureView.targetUser.setValue(user1);
        EventSender.sendMessage("Начат режим диалога с: " + user1.getLogin() + "[" + user1.getId() + "]\"\n" +
                "Для выхода из диалога введите команду \"/leave\"");
    }

    public void leave() {
        User1 user1 = StructureView.targetUser.get();
        if (user1 != null) {
            StructureView.targetUser.setValue(null);
            EventSender.sendMessage("Закончен диалог с: " + user1.getLogin() + "[" + user1.getId() + "]\"\n");
        }
    }
}
