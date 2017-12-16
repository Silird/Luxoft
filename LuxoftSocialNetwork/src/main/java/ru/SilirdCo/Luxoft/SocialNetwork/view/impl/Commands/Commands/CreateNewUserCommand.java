package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receiver;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Network;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.User;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

public class CreateNewUserCommand implements ICommand {
    private final Receiver receiver;

    public CreateNewUserCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        EventSender.sendMessage("Создание пользователя");
        User user = new User();
        Network network = receiver.getNetwork();
        network.addUser(user);

        EventSender.sendMessage("Пользователь добавлен в сеть, количество пользователей в сети: " + network.getUsers().size());
    }
}
